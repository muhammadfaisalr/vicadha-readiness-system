package id.muhammadfaisal.vicadhareadinesssystem.adapter

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.muhammadfaisal.vicadhareadinesssystem.R
import id.muhammadfaisal.vicadhareadinesssystem.databinding.ItemMemberBinding
import id.muhammadfaisal.vicadhareadinesssystem.helper.AuthHelper
import id.muhammadfaisal.vicadhareadinesssystem.helper.DatabaseHelper
import id.muhammadfaisal.vicadhareadinesssystem.helper.GeneralHelper
import id.muhammadfaisal.vicadhareadinesssystem.room.entity.UserEntity
import id.muhammadfaisal.vicadhareadinesssystem.ui.Loading
import id.muhammadfaisal.vicadhareadinesssystem.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MemberAdapter(var context: Context, var users: List<UserEntity>): RecyclerView.Adapter<MemberAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private var binding = ItemMemberBinding.bind(this.itemView)
        private lateinit var context: Context
        private lateinit var userEntity: UserEntity

        fun bind(context: Context, userEntity: UserEntity, i: Int) {
            this.context = context
            this.userEntity = userEntity

            this.binding.apply {
                this.textAvatar.text = GeneralHelper.textAvatar(userEntity.name)
                this.textName.text = userEntity.name
                this.textPhone.text = userEntity.phoneNumber.toString()


                this.buttonDelete.setOnClickListener(this@ViewHolder)
            }

            this.itemView.setOnClickListener(this)
        }

        private suspend fun deleteFromDb() {
            delay(100L)

            val adminEmail = SharedPreferences.get(context, Constant.Key.USER_EMAIL)
            val adminPassword = SharedPreferences.get(context, Constant.Key.USER_PASSWORD)


            AuthHelper
                .Account
                .signInWithEmailPassword(userEntity.email, Encrypter.decryptString(userEntity.password))
                .addOnSuccessListener {
                    val currentUser = it.user
                    currentUser?.delete()?.addOnSuccessListener {
                        DatabaseHelper
                            .Firebase
                            .getAccountReference(userEntity.key!!)
                            .removeValue()
                            .addOnSuccessListener {
                                Log.d(MemberAdapter::class.simpleName, "Success Delete Account!")
                                AuthHelper
                                    .Account
                                    .signInWithEmailPassword(adminEmail.toString(), Encrypter.decryptString(adminPassword.toString()))
                                    .addOnSuccessListener {
                                        Log.d(MemberAdapter::class.simpleName, "Success Signing back Admin Account.")
                                    }
                            }.addOnFailureListener {
                                Log.e(MemberAdapter::class.simpleName, "Fail to delete account because $it")

                            }
                    }?.addOnFailureListener {
                        Log.e(MemberAdapter::class.simpleName, "Fail to delete account because $it")
                        BottomSheets.error(
                            context,
                            context.getString(R.string.something_wrong),
                            it.message,
                            true
                        )
                    }
                }
        }

        override fun onClick(p0: View?) {

            if (p0 == this.binding.buttonDelete) {
                this.processDelete()
            } else {
                val bundle = Bundle()
                bundle.putString(Constant.Key.OPEN_FROM, MemberAdapter::class.java.simpleName)
                bundle.putString(Constant.Key.USER_EMAIL, this.userEntity.email)

                MoveTo.editProfile(this.context, bundle, false)
            }
        }

        private fun processDelete() {
            val loading = Loading(this.context)
            loading.setCancelable(false)
            loading.show()

            Log.d(MemberAdapter::class.simpleName, "Trying to delete user ${userEntity.key} at ${userEntity.groupName}")

            //Start Deleting data From Local DB.
            CoroutineScope(IO).launch {
                deleteFromDb()
            }


            Handler(Looper.myLooper()!!).postDelayed({
                loading.dismiss()

                val bundle = Bundle()
                bundle.putString(Constant.Key.SUCCESS_TITLE, context.getString(R.string.success_delete_member))
                MoveTo.success(context, bundle, true)
            }, 5000L)

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