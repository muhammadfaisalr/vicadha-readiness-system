package id.muhammadfaisal.vicadhareadinesssystem.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.muhammadfaisal.vicadhareadinesssystem.databinding.FragmentMemberBinding
import id.muhammadfaisal.vicadhareadinesssystem.helper.DatabaseHelper
import id.muhammadfaisal.vicadhareadinesssystem.helper.GeneralHelper
import id.muhammadfaisal.vicadhareadinesssystem.utils.Constant
import id.muhammadfaisal.vicadhareadinesssystem.utils.MoveTo
import id.muhammadfaisal.vicadhareadinesssystem.adapter.MemberAdapter

class MemberFragment() : Fragment(), View.OnClickListener, TextWatcher {

    private lateinit var binding: FragmentMemberBinding
    private lateinit var groupName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentMemberBinding.inflate(this.layoutInflater)
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = this.requireArguments().getString(Constant.Key.GROUP_NAME)!!
        this.groupName = data

        val userDao = DatabaseHelper.RoomDb.userDao(requireContext())

        this.binding.apply {
            this.recyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            this.recyclerView.adapter = MemberAdapter(requireContext(), userDao.getAllByGroupName(groupName))
            this.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))

            this.inputSearch.addTextChangedListener(this@MemberFragment)

            GeneralHelper.makeClickable(this@MemberFragment, this.exfabAddMember)
        }
    }

    override fun onClick(p0: View?) {
        if (p0 == this.binding.exfabAddMember) {
            val bundle = Bundle()
            bundle.putString(Constant.Key.FOR_GROUP, this.groupName)
            bundle.putInt(Constant.Key.ROLE_ID, Constant.Role.MEMBER)
            MoveTo.addMember(requireContext(), bundle, false)
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun afterTextChanged(p0: Editable?) {
        val userDao = DatabaseHelper.RoomDb.userDao(requireContext())

        if (p0 != null) {
            if (p0.isNotEmpty()) {
                val s = p0.toString()

                this.binding.recyclerView.adapter = MemberAdapter(requireContext(), userDao.query(GeneralHelper.queryFormat(s), groupName))
            } else {
                this.binding.recyclerView.adapter = MemberAdapter(requireContext(), userDao.getAllByGroupName(groupName))
            }
        }
    }
}