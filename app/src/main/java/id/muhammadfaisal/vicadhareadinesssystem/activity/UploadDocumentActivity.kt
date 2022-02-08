package id.muhammadfaisal.vicadhareadinesssystem.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import id.muhammadfaisal.vicadhareadinesssystem.databinding.ActivityUploadDocumentBinding
import id.muhammadfaisal.vicadhareadinesssystem.helper.DatabaseHelper
import id.muhammadfaisal.vicadhareadinesssystem.helper.GeneralHelper

import id.muhammadfaisal.vicadhareadinesssystem.R
import id.muhammadfaisal.vicadhareadinesssystem.firebase.model.DocumentFirebase
import id.muhammadfaisal.vicadhareadinesssystem.ui.Loading
import id.muhammadfaisal.vicadhareadinesssystem.utils.*


class UploadDocumentActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityUploadDocumentBinding
    private lateinit var resultLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var loading: Loading

    private var requestCode = 0
    private var documentUrl = ""
    private var groupName = ""

    companion object {
        const val SELECT_DOC = 999
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityUploadDocumentBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        this.extract()

        this.loading = Loading(this)
        this.loading.setCancelable(false)

        this.resultLauncher = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            if (uri != null) {
                upload(uri)
            }
        }
        this.binding.apply {
            GeneralHelper.makeClickable(this@UploadDocumentActivity, this.buttonUploadDoc, this.exfabSend)
        }
    }

    private fun extract() {
        val bundle = this.intent.getBundleExtra(Constant.Key.BUNDLING)!!
        this.groupName = bundle.getString(Constant.Key.GROUP_NAME)!!
    }

    override fun onClick(p0: View?) {
        if (p0 == this.binding.buttonUploadDoc) {
            this.selectDoc()
        }

        if (p0 == this.binding.exfabSend) {
            this.send()
        }
    }

    private fun send() {
        this.loading.show()
        val title = this.binding.inputTitle.text.toString()

        if (this.documentUrl.isEmpty() || title.isEmpty()) {
            this.loading.dismiss()
            BottomSheets.error(this, getString(R.string.something_wrong), getString(R.string.make_sure_fill_was_filled), true)
            return
        }

        val sender = SharedPreferences.get(this, Constant.Key.USER_NAME)
        val document = DocumentFirebase(this.documentUrl, title, sender.toString())
        val documents = ArrayList<DocumentFirebase>()
        var isCanContinue = true

        DatabaseHelper
            .Firebase
            .getDocumentReference(this.groupName)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (data in snapshot.children) {
                        val documentChildren = data.getValue(DocumentFirebase::class.java)
                        if (documentChildren != null) {
                            documents.add(documentChildren)
                        }
                    }

                    documents.add(document)

                    if (isCanContinue) {
                        DatabaseHelper
                            .Firebase
                            .getDocumentReference(groupName)
                            .setValue(documents)
                            .addOnSuccessListener {
                                loading.dismiss()
                                Log.d(UploadDocumentActivity::class.java.simpleName, "Upload to Firebase Success.")

                                MessagingService.sendMessage("Dokumen Baru!","Dokumen Baru Telah Di Lampirkan, Segera Periksa!",groupName, GeneralHelper.setAsGroupId(this@UploadDocumentActivity.groupName))

                                //Send Notification to SuperAdmin
                                MessagingService.sendMessage("Dokumen Baru Pada $groupName!", "Dokumen Baru Telah Di Lampirkan Di Group $groupName, Segera Periksa!", groupName, GeneralHelper.setAsGroupId(Constant.RoleName.SUPER_ADMIN))

                                val bundle = Bundle()
                                bundle.putString(
                                    Constant.Key.SUCCESS_TITLE,
                                    getString(R.string.document_uploaded)
                                )

                                MoveTo.success(this@UploadDocumentActivity, bundle, true)
                            }
                    }

                    isCanContinue = false
                }

                override fun onCancelled(error: DatabaseError) {
                    loading.dismiss()
                    BottomSheets.error(this@UploadDocumentActivity, error.message, error.details, true)
                }

            })
    }

    private fun selectDoc() {
        this.requestCode = SELECT_DOC
        this.resultLauncher.launch(
            arrayOf("*/*")
        )
    }

    private fun upload(uri: Uri) {
        this.loading.show()
        Log.d(UploadDocumentActivity::class.java.simpleName, "Get URI Data $uri")

        val mime = MimeTypeMap.getSingleton()
        val typeFile = mime.getExtensionFromMimeType(contentResolver.getType(uri))
        val storageReference = FirebaseStorage.getInstance().reference
        val filePath = storageReference.child(DatabaseHelper.Endpoint.DOCUMENT).child("Dokumen-$groupName-${System.currentTimeMillis()}.$typeFile")

        filePath.putFile(uri)
            .addOnSuccessListener { it1 ->
                this.loading.dismiss()
                this.binding.buttonUploadDoc.isEnabled = false
                this.binding.buttonUploadDoc.text = "Dokumen telah dipilih"
                this.binding.exfabSend.isEnabled = true

                filePath.downloadUrl.addOnSuccessListener { downloadUri ->
                    Log.d(UploadDocumentActivity::class.java.simpleName, "Upload $typeFile Success, The URL is $downloadUri")
                    this.documentUrl = downloadUri.toString()
                }
            }.addOnFailureListener{ it1 ->
                this.loading.dismiss()
                Log.d(UploadDocumentActivity::class.java.simpleName, "Upload Failed.")
                BottomSheets.error(this, getString(R.string.something_wrong), it1.message, true)
            }
    }
}