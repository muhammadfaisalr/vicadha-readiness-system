package id.muhammadfaisal.vicadhareadinesssystem.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import id.muhammadfaisal.vicadhareadinesssystem.R
import id.muhammadfaisal.vicadhareadinesssystem.databinding.ItemDocumentBinding
import id.muhammadfaisal.vicadhareadinesssystem.firebase.model.DocumentFirebase
import id.muhammadfaisal.vicadhareadinesssystem.helper.DatabaseHelper
import id.muhammadfaisal.vicadhareadinesssystem.helper.GeneralHelper
import id.muhammadfaisal.vicadhareadinesssystem.ui.Loading
import id.muhammadfaisal.vicadhareadinesssystem.utils.BottomSheets
import id.muhammadfaisal.vicadhareadinesssystem.utils.Constant
import id.muhammadfaisal.vicadhareadinesssystem.utils.MoveTo

class DocumentAdapter(
    val context: Context,
    val documents: ArrayList<DocumentFirebase>,
    val groupName: String
) : RecyclerView.Adapter<DocumentAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private lateinit var context: Context
        private lateinit var document: DocumentFirebase
        private lateinit var groupName: String

        private var binding = ItemDocumentBinding.bind(itemView)

        fun bind(context: Context, documentFirebase: DocumentFirebase, groupName: String) {
            this.groupName = groupName
            this.context = context
            this.document = documentFirebase
            this.binding.apply {
                this.textAvatar.text = GeneralHelper.textAvatar(documentFirebase.title)
                this.textTitle.text = documentFirebase.title
                this.textSender.text = documentFirebase.sender
            }

            GeneralHelper.makeClickable(this, this.itemView, this.binding.buttonDelete)
        }

        override fun onClick(p0: View?) {
            if (p0 == this.itemView) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(document.url)
                context.startActivity(intent)
            }

            if (p0 == this.binding.buttonDelete) {
                this.delete(this.context, this.document, this.groupName)
            }
        }

        private fun delete(context: Context, document: DocumentFirebase, groupName: String) {
            val loading = Loading(context)
            loading.setCancelable(false)
            loading.show()

            val documents = ArrayList<DocumentFirebase>()
            var docId: String? = null

            DatabaseHelper.Firebase.getDocumentReference(groupName)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (docSnapshot in snapshot.children) {
                            val doc = docSnapshot.getValue(DocumentFirebase::class.java)!!

                            if (doc.title == document.title) {
                                Log.d(
                                    DocumentAdapter::class.java.simpleName,
                                    "Document ${doc.title} Has Been Deleted."
                                )
                                docId = docSnapshot.key
                            } else {
                                Log.d(
                                    DocumentAdapter::class.java.simpleName,
                                    "Adding Document ${doc.title} to Firebase."
                                )
                                documents.add(document)
                            }

                            DatabaseHelper.Firebase.getDocumentReference(groupName).removeValue()
                                .addOnSuccessListener {
                                    DatabaseHelper.Firebase.getDocumentReference(groupName)
                                        .setValue(documents).addOnSuccessListener {
                                        loading.dismiss()
                                        val bundle = Bundle()
                                        bundle.putString(
                                            Constant.Key.SUCCESS_TITLE,
                                            context.resources.getString(R.string.success_delete_document)
                                        )
                                        MoveTo.success(context, bundle, true)
                                    }
                                        .addOnFailureListener {
                                            Log.e(
                                                DocumentAdapter::class.java.simpleName,
                                                "Error ${it.message}"
                                            )
                                            BottomSheets.error(
                                                context,
                                                context.resources.getString(R.string.something_wrong),
                                                it.message,
                                                true
                                            )
                                        }
                                }
                                .addOnFailureListener {
                                    Log.e(
                                        DocumentAdapter::class.java.simpleName,
                                        "Error ${it.message}"
                                    )
                                    BottomSheets.error(
                                        context,
                                        context.resources.getString(R.string.something_wrong),
                                        it.message,
                                        true
                                    )
                                }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_document, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.context, this.documents[position], this.groupName)
    }

    override fun getItemCount(): Int {
        return this.documents.size
    }
}