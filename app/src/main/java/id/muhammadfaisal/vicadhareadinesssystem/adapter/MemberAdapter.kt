package id.muhammadfaisal.vicadhareadinesssystem.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.muhammadfaisal.vicadhareadinesssystem.R
import id.muhammadfaisal.vicadhareadinesssystem.databinding.ItemMemberBinding
import id.muhammadfaisal.vicadhareadinesssystem.helper.GeneralHelper
import id.muhammadfaisal.vicadhareadinesssystem.room.entity.UserEntity

class MemberAdapter(var context: Context, var users: List<UserEntity>): RecyclerView.Adapter<MemberAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var binding = ItemMemberBinding.bind(this.itemView)

        fun bind(context: Context, userEntity: UserEntity, i: Int) {
            this.binding.apply {
                this.textAvatar.text = GeneralHelper.textAvatar(userEntity.name)
                this.textName.text = userEntity.name
                this.textPhone.text = userEntity.phoneNumber.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_member, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, users[position], position + 1)
    }

    override fun getItemCount(): Int {
        return users.size
    }
}