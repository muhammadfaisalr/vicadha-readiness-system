package id.muhammadfaisal.vicadhareadinesssystem.bottomsheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.muhammadfaisal.vicadhareadinesssystem.R
import id.muhammadfaisal.vicadhareadinesssystem.adapter.GroupAdapter
import id.muhammadfaisal.vicadhareadinesssystem.databinding.FragmentBottomSheetChildGroupBinding
import id.muhammadfaisal.vicadhareadinesssystem.helper.DatabaseHelper

class BottomSheetChildGroup(var groupName: String) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentBottomSheetChildGroupBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentBottomSheetChildGroupBinding.inflate(this.layoutInflater)
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.binding.apply {
            this.textTitle.text = groupName

            this.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
            this.recyclerView.adapter = GroupAdapter(requireActivity() as AppCompatActivity, DatabaseHelper.RoomDb.groupDao(requireContext()).getAllChild(groupName))
        }
    }
}