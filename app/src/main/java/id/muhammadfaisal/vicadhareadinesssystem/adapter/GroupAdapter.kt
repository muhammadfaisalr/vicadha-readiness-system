package id.muhammadfaisal.vicadhareadinesssystem.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import id.muhammadfaisal.vicadhareadinesssystem.R
import id.muhammadfaisal.vicadhareadinesssystem.databinding.ItemGroupBinding
import id.muhammadfaisal.vicadhareadinesssystem.helper.DatabaseHelper
import id.muhammadfaisal.vicadhareadinesssystem.helper.GeneralHelper
import id.muhammadfaisal.vicadhareadinesssystem.room.entity.GroupEntity
import id.muhammadfaisal.vicadhareadinesssystem.utils.BottomSheets
import id.muhammadfaisal.vicadhareadinesssystem.utils.Constant
import id.muhammadfaisal.vicadhareadinesssystem.utils.MoveTo

class GroupAdapter(var context: AppCompatActivity, var groups: List<GroupEntity>) :
    RecyclerView.Adapter<GroupAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private var binding = ItemGroupBinding.bind(itemView)
        private lateinit var contextActivity: AppCompatActivity

        fun bind(context: AppCompatActivity, groupEntity: GroupEntity) {

            val userDao = DatabaseHelper.RoomDb.userDao(context)
            val users = userDao.getAllByGroupName(groupEntity.name)
            this.contextActivity = context

            this.binding.apply {
                this.imageView.setImageResource(groupEntity.image)
                this.textTitle.text = groupEntity.name
                this.textTotalAnggota.text = "${users.size} Anggota"
            }

            GeneralHelper.makeClickable(this, binding.root)
        }

        override fun onClick(p0: View?) {
            if (p0 == binding.root) {
                val bundle = Bundle()
                bundle.putString(Constant.Key.GROUP_NAME, this.binding.textTitle.text.toString())
                MoveTo.detailGroup(contextActivity, bundle, false)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_group, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, groups[position])
    }

    override fun getItemCount(): Int {
        return groups.size
    }
}