package id.muhammadfaisal.vicadhareadinesssystem.fragment

import android.os.Bundle
import android.os.Message
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
import id.muhammadfaisal.vicadhareadinesssystem.adapter.InboxAdapter
import id.muhammadfaisal.vicadhareadinesssystem.databinding.FragmentInboxBinding
import id.muhammadfaisal.vicadhareadinesssystem.firebase.model.MessageFirebase
import id.muhammadfaisal.vicadhareadinesssystem.helper.DatabaseHelper
import id.muhammadfaisal.vicadhareadinesssystem.helper.GeneralHelper
import id.muhammadfaisal.vicadhareadinesssystem.utils.BottomSheets
import id.muhammadfaisal.vicadhareadinesssystem.utils.Constant
import id.muhammadfaisal.vicadhareadinesssystem.utils.SharedPreferences

class InboxFragment(var groupName: String) : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentInboxBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentInboxBinding.inflate(this.layoutInflater)
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.setup()
        val role = SharedPreferences.get(requireContext(), Constant.Key.ROLE_ID)

        if (role != Constant.Role.SUPER_ADMIN) {
            this.binding.apply {
                this.exfabWriteMessage.visibility = View.GONE
            }
        }
        this.binding.apply {
            GeneralHelper.makeClickable(this@InboxFragment, this.exfabWriteMessage)
        }
    }

    private fun setup() {
        val messages = ArrayList<MessageFirebase>()
        DatabaseHelper
            .Firebase
            .getInboxReference(groupName)
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (snapshotChildren in snapshot.children) {
                        val message = snapshotChildren.getValue(MessageFirebase::class.java)
                        messages.add(message!!)
                    }

                    this@InboxFragment.binding.apply {
                        this.recyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                        this.recyclerView.adapter = InboxAdapter(requireContext(), messages)
                        this.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL  ))
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
    }

    override fun onClick(p0: View?) {
        if (p0 == this.binding.exfabWriteMessage) {
            BottomSheets.sendMessage(requireContext(), groupName)
        }
    }
}