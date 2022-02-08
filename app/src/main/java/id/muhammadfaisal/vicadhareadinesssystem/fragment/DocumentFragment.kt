package id.muhammadfaisal.vicadhareadinesssystem.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import id.muhammadfaisal.vicadhareadinesssystem.adapter.DocumentAdapter
import id.muhammadfaisal.vicadhareadinesssystem.databinding.FragmentDocumentBinding
import id.muhammadfaisal.vicadhareadinesssystem.firebase.model.DocumentFirebase
import id.muhammadfaisal.vicadhareadinesssystem.helper.DatabaseHelper
import id.muhammadfaisal.vicadhareadinesssystem.helper.GeneralHelper
import id.muhammadfaisal.vicadhareadinesssystem.utils.Constant
import id.muhammadfaisal.vicadhareadinesssystem.utils.MoveTo
import id.muhammadfaisal.vicadhareadinesssystem.utils.SharedPreferences

class DocumentFragment() : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentDocumentBinding
    private var groupName: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentDocumentBinding.inflate(this.layoutInflater)
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.groupName = this.requireArguments().getString(Constant.Key.GROUP_NAME, "")

        this.data()
        this.binding.apply {
            val role = SharedPreferences.get(requireContext(), Constant.Key.ROLE_ID)

            if (role == Constant.Role.MEMBER) {
                this.exfabUploadDoc.visibility = View.GONE
            }
            
            GeneralHelper.makeClickable(this@DocumentFragment, this.exfabUploadDoc)
        }
    }

    private fun data() {
        val documents = ArrayList<DocumentFirebase>()
        DatabaseHelper
            .Firebase
            .getDocumentReference(this.groupName)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (data in snapshot.children) {
                        val document = data.getValue(DocumentFirebase::class.java)
                        if (document != null) {
                            documents.add(document)
                        }
                    }

                    try {
                        this@DocumentFragment.binding.apply {
                            this.recyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                            this.recyclerView.adapter = DocumentAdapter(requireContext(), documents, groupName)
                            this.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
                        }
                    }catch (e: Exception) {
                        Log.d(DocumentFragment::class.java.simpleName, e.message!!)
                    }


                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
    }

    override fun onClick(p0: View?) {
        if (p0 == this.binding.exfabUploadDoc) {
            val bundle = Bundle()
            bundle.putString(Constant.Key.GROUP_NAME, this.groupName)
            MoveTo.uploadDocument(requireContext(), bundle, false)
        }
    }
}