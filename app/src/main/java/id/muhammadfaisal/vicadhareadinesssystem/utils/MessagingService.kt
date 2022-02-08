package id.muhammadfaisal.vicadhareadinesssystem.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import id.muhammadfaisal.vicadhareadinesssystem.R
import id.muhammadfaisal.vicadhareadinesssystem.activity.DetailGroupActivity
import id.muhammadfaisal.vicadhareadinesssystem.activity.DetailMessageActivity
import id.muhammadfaisal.vicadhareadinesssystem.activity.MainActivity
import id.muhammadfaisal.vicadhareadinesssystem.firebase.model.MessageFirebase
import id.muhammadfaisal.vicadhareadinesssystem.helper.DatabaseHelper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.*
import java.net.URL
import javax.net.ssl.HttpsURLConnection


class MessagingService : FirebaseMessagingService() {

    companion object {
        var token: String? = null
        private const val Key = "AAAAEch38q4:APA91bEiZjgSqutuQHmiPPdBNywBSFE7HXkcGqH-4GQWF1MtReW_vZA6gITHM6mZf-SOPDPA84OwZB-lJvlXKABTX-FTAJMi2qPYmmU7t-uigYBwkP6yHGMh7FAPs-7CVW2nTGEGzqac"

        fun sendMessage(title: String, content: String, groupName: String, topic: String) {
            GlobalScope.launch {
                val endpoint = "https://fcm.googleapis.com/fcm/send"
                try {
                    val url = URL(endpoint)
                    val httpsURLConnection: HttpsURLConnection =
                        url.openConnection() as HttpsURLConnection
                    httpsURLConnection.readTimeout = 10000
                    httpsURLConnection.connectTimeout = 15000
                    httpsURLConnection.requestMethod = "POST"
                    httpsURLConnection.doInput = true
                    httpsURLConnection.doOutput = true

                    // Adding the necessary headers
                    httpsURLConnection.setRequestProperty("authorization", "key=$Key")
                    httpsURLConnection.setRequestProperty("Content-Type", "application/json")

                    // Creating the JSON with post params
                    val body = JSONObject()

                    val data = JSONObject()
                    data.put("title", title)
                    data.put("content", content)
                    data.put("group", groupName)
                    body.put("data", data)

                    body.put("to", "/topics/$topic")

                    val outputStream: OutputStream =
                        BufferedOutputStream(httpsURLConnection.outputStream)
                    val writer = BufferedWriter(OutputStreamWriter(outputStream, "utf-8"))
                    writer.write(body.toString())
                    writer.flush()
                    writer.close()
                    outputStream.close()
                    val responseCode: Int = httpsURLConnection.responseCode
                    val responseMessage: String = httpsURLConnection.responseMessage
                    Log.d("Response:", "$responseCode $responseMessage")
                    var result = String()
                    var inputStream: InputStream? = null
                    inputStream = if (responseCode in 400..499) {
                        httpsURLConnection.errorStream
                    } else {
                        httpsURLConnection.inputStream
                    }

                    if (responseCode == 200) {
                        Log.e("Success:", "notification sent $title \n $content")
                        // The details of the user can be obtained from the result variable in JSON format
                    } else {
                        Log.e("Error", "Error Response")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        fun sendMessage(message: MessageFirebase, groupName: String, topic: String) {
            GlobalScope.launch {
                val endpoint = "https://fcm.googleapis.com/fcm/send"
                try {
                    val url = URL(endpoint)
                    val httpsURLConnection: HttpsURLConnection =
                        url.openConnection() as HttpsURLConnection
                    httpsURLConnection.readTimeout = 10000
                    httpsURLConnection.connectTimeout = 15000
                    httpsURLConnection.requestMethod = "POST"
                    httpsURLConnection.doInput = true
                    httpsURLConnection.doOutput = true

                    // Adding the necessary headers
                    httpsURLConnection.setRequestProperty("authorization", "key=$Key")
                    httpsURLConnection.setRequestProperty("Content-Type", "application/json")

                    // Creating the JSON with post params
                    val body = JSONObject()

                    val data = JSONObject()
                    data.put("title", message.title)
                    data.put("content", message.message)
                    data.put("sender", message.sender)
                    data.put("date", message.date)
                    data.put("type", message.messageType)
                    data.put("group", groupName)

                    body.put("data", data)

                    body.put("to", "/topics/$topic")

                    val outputStream: OutputStream =
                        BufferedOutputStream(httpsURLConnection.outputStream)
                    val writer = BufferedWriter(OutputStreamWriter(outputStream, "utf-8"))
                    writer.write(body.toString())
                    writer.flush()
                    writer.close()
                    outputStream.close()
                    val responseCode: Int = httpsURLConnection.responseCode
                    val responseMessage: String = httpsURLConnection.responseMessage
                    Log.d("Response:", "$responseCode $responseMessage")
                    var result = String()
                    var inputStream: InputStream? = null
                    inputStream = if (responseCode in 400..499) {
                        httpsURLConnection.errorStream
                    } else {
                        httpsURLConnection.inputStream
                    }

                    if (responseCode == 200) {
                        Log.e("Success:", "notification sent ${message.title} \n $message.mes")
                        // The details of the user can be obtained from the result variable in JSON format
                    } else {
                        Log.e("Error", "Error Response")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        Log.d("onMessageReceived", p0.data["title"].toString())

        val title = p0.data["title"]
        val content = p0.data["content"]
        val sender = p0.data["sender"]
        val groupName = p0.data["group"]
        val date = p0.data["date"]
        val type = p0.data["type"]

        var channelId = 0

        val sound: Uri?

        when (title) {
            getString(R.string.apel_kesiapsiagaan) -> {
                channelId = 9000101
                sound =
                    Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE+ "://" + packageName+"/"+R.raw.apel_kesiapsiagaan)
            }
            getString(R.string.alarm_stelling) -> {
                channelId = 9000102
                sound =
                    Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE+ "://" + packageName+"/"+R.raw.alarm_stelling)
            }
            getString(R.string.apel_luar_biasa) -> {
                channelId = 9000103
                sound =
                    Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE+ "://" + packageName+"/"+R.raw.apel_luar_biasa)
            }
            getString(R.string.bahaya_kebakaran) -> {
                channelId = 9000104
                sound =
                    Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE+ "://" + packageName+"/"+R.raw.bahaya_kebakaran)
            }
            getString(R.string.bantuan_kepolisian) -> {
                channelId = 9000105
                sound =
                    Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE+ "://" + packageName+"/"+R.raw.bantuan_kepolisian)
            }
            getString(R.string.bencana_alam) -> {
                channelId = 9000106
                sound =
                    Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE+ "://" + packageName+"/"+R.raw.bencana_alam)
            }
            getString(R.string.tanda_aman) -> {
                channelId = 9000107
                sound =
                    Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE+ "://" + packageName+"/"+R.raw.tanda_aman)
            }else -> {
                channelId = 9000108 
                sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            }
        }

        val groupDao = DatabaseHelper.RoomDb.groupDao(this)


        Log.d(MessagingService::class.java.simpleName, "Send Message for $groupName Group!.")

        val roleId = SharedPreferences.get(this, Constant.Key.ROLE_ID)

        val intent: Intent?
        when (roleId) {
            Constant.Role.MEMBER -> {
                val bundle = Bundle()
                bundle.putString(Constant.Key.GROUP_NAME, groupName)

                intent = Intent(this, DetailGroupActivity::class.java)
                intent.putExtra(Constant.Key.BUNDLING, bundle)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            else -> {
                if (sender != null) {
                    Log.d(MessagingService::class.java.simpleName, "$sender Is A Sender In this Notification")
                    val bundleInner = Bundle()
                    bundleInner.putSerializable(Constant.Key.MESSAGE, MessageFirebase(title!!, content!!, sender, date!!.toLong(), type!!.toInt()))

                    intent = Intent(this, DetailMessageActivity::class.java)
                    intent.putExtra(Constant.Key.BUNDLING, bundleInner)
                } else {
                    intent = Intent(this, DetailGroupActivity::class.java)
                }

                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
        }

        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            checkNotificationChannel(channelId.toString(), sound)
        }

        val notification = NotificationCompat.Builder(applicationContext, channelId.toString())
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(content)
            .setContentIntent(pendingIntent)
            .setDefaults(Notification.DEFAULT_LIGHTS)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(sound, AudioManager.STREAM_NOTIFICATION)

        val notificationBuild = notification.build()
        val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(channelId, notificationBuild)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkNotificationChannel(CHANNEL_ID: String, sound: Uri) {
        val audioAttribute = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
            .build()

        val notificationChannel = NotificationChannel(
            CHANNEL_ID,
            getString(R.string.app_name),
            NotificationManager.IMPORTANCE_HIGH
        )

        notificationChannel.description = "CHANNEL_DESCRIPTION"
        notificationChannel.setSound(sound, audioAttribute)
        notificationChannel.enableLights(true)
        notificationChannel.enableVibration(true)
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(notificationChannel)
    }

    override fun onNewToken(p0: String) {
        token = p0
        super.onNewToken(p0)
    }
}