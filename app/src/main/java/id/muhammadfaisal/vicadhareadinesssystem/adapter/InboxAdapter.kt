package id.muhammadfaisal.vicadhareadinesssystem.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.muhammadfaisal.vicadhareadinesssystem.R
import id.muhammadfaisal.vicadhareadinesssystem.databinding.ItemInboxBinding
import id.muhammadfaisal.vicadhareadinesssystem.helper.GeneralHelper
import id.muhammadfaisal.vicadhareadinesssystem.utils.MoveTo

class InboxAdapter(var context: Context) : RecyclerView.Adapter<InboxAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private var binding = ItemInboxBinding.bind(this.itemView)

        fun bind(context: Context) {
            GeneralHelper.makeClickable(this, this.binding.root)
        }

        override fun onClick(p0: View?) {
            if (p0 == this.binding.root) {
                MoveTo.detailMessage(this.binding.root.context, null, false)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_inbox, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context)
    }

    override fun getItemCount(): Int {
        return 6
    }
}