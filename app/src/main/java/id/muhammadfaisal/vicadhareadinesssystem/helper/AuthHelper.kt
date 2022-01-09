package id.muhammadfaisal.vicadhareadinesssystem.helper

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import id.muhammadfaisal.vicadhareadinesssystem.firebase.model.UserFirebase

class AuthHelper {
    companion object {
        fun getCurrentUser(): FirebaseUser? {
            return FirebaseAuth.getInstance().currentUser
        }

        fun getUid(): String? {
            return FirebaseAuth.getInstance().currentUser?.uid
        }
    }

    class Account {
        companion object {

            const val API_KEY = "AIzaSyCwXnUw79FdvhwPPaNr1mdIuYOs0C155gQ"
            const val DB_URL = "https://vicadha-readiness-system-default-rtdb.europe-west1.firebasedatabase.app/"
            const val APP_ID = "vicadha -readiness-system"

            fun signInWithEmailPassword(email: String, password: String): Task<AuthResult> {
                return FirebaseAuth
                    .getInstance()
                    .signInWithEmailAndPassword(email, password)
            }

            fun createAccount(user: UserFirebase): Task<AuthResult> {
                return FirebaseAuth
                    .getInstance()
                    .createUserWithEmailAndPassword(user.email!!, user.password!!)
            }

            fun updatePassword(user: UserFirebase): Task<Void> {
                return getCurrentUser()!!.updatePassword(user.password!!)
            }

            fun logout() {
                return FirebaseAuth.getInstance().signOut()
            }
        }
    }
}