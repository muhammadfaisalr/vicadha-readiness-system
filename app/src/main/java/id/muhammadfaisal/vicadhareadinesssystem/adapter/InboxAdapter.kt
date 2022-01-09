package id.muhammadfaisal.vicadhareadinesssystem.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.muhammadfaisal.vicadhareadinesssystem.R
import id.muhammadfaisal.vicadhareadinesssystem.databinding.ItemInboxBinding
import id.muhammadfaisal.vicadhareadinesssystem.firebase.model.MessageFirebase
import id.muhammadfaisal.vicadhareadinesssystem.helper.GeneralHelper
import id.muhammadfaisal.vicadhareadinesssystem.utils.MoveTo

class InboxAdapter(var context: Context, var messages: ArrayList<MessageFirebase>) : RecyclerView.Adapter<InboxAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private var binding = ItemInboxBinding.bind(this.itemView)

        fun bind(context: Context, messageFirebase: MessageFirebase) {
            this.binding.apply {
                this.textTitle.text = messageFirebase.title
            }
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
        holder.bind(context, messages[position])
    }

    override fun getItemCount(): Int {
        return messages.size
    }
}