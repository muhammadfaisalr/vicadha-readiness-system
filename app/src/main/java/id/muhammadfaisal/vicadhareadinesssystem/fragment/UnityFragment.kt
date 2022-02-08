package id.muhammadfaisal.vicadhareadinesssystem.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import id.muhammadfaisal.vicadhareadinesssystem.R
import id.muhammadfaisal.vicadhareadinesssystem.adapter.GroupAdapter
import id.muhammadfaisal.vicadhareadinesssystem.databinding.FragmentUnityBinding
import id.muhammadfaisal.vicadhareadinesssystem.helper.DatabaseHelper
import id.muhammadfaisal.vicadhareadinesssystem.utils.Constant

class UnityFragment : Fragment() {

    private lateinit var binding: FragmentUnityBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentUnityBinding.inflate(this.layoutInflater)
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val groupName = this.requireArguments().getString(Constant.Key.GROUP_NAME)!!

        this.binding.apply {
            this.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
            this.recyclerView.adapter = GroupAdapter(requireActivity() as AppCompatActivity, DatabaseHelper.RoomDb.groupDao(requireContext()).getChildren(groupName))
        }
    }
}