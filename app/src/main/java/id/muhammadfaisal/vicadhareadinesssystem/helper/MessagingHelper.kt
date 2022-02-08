package id.muhammadfaisal.vicadhareadinesssystem.helper

import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging

class MessagingHelper {
    companion object{
        fun subscribe(topic : String): Task<Void> {
            return FirebaseMessaging.getInstance().subscribeToTopic(topic)
        }

        fun unsubscribe(topic: String) : Task<Void> {
            return FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
        }
    }
}