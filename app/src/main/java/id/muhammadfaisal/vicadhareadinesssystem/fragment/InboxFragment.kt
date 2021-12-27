package id.muhammadfaisal.vicadhareadinesssystem.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.muhammadfaisal.vicadhareadinesssystem.R
import id.muhammadfaisal.vicadhareadinesssystem.adapter.InboxAdapter
import id.muhammadfaisal.vicadhareadinesssystem.databinding.FragmentInboxBinding
import id.muhammadfaisal.vicadhareadinesssystem.helper.GeneralHelper
import id.muhammadfaisal.vicadhareadinesssystem.utils.BottomSheets

class InboxFragment : Fragment(), View.OnClickListener {

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

        this.binding.apply {
            this.recyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            this.recyclerView.adapter = InboxAdapter(requireContext())
            this.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL  ))

            GeneralHelper.makeClickable(this@InboxFragment, this.exfabWriteMessage)
        }
    }

    override fun onClick(p0: View?) {
        if (p0 == this.binding.exfabWriteMessage) {
            BottomSheets.sendMessage(requireContext(), null)
        }
    }
}