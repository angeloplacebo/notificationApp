package com.angeloplacebo.notificationapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.text.HtmlCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val title = remoteMessage.notification?.title
        val body = remoteMessage.notification?.body

        handleShowNotification(applicationContext,title,body)

    }

    override fun onNewToken(remoteMessage: String) {
        super.onNewToken(remoteMessage)
        Log.e("Notification_APP", "new token recebido")

    }

    fun handleShowNotification(context: Context,title: String?, message: String?){
//        val titulo = HtmlCompat.fromHtml("<strong> Titulo da notificação</strong>", HtmlCompat.FROM_HTML_MODE_LEGACY)
//        val descricao = "Descricao da notificação"
//        val corpo = "Corpo da notificação"

        val title = HtmlCompat.fromHtml("<strong> ${title} </strong>", HtmlCompat.FROM_HTML_MODE_LEGACY)

        val notificationChannel : NotificationChannel
        val builder : NotificationCompat.Builder
        val channelId = "com.angeloplacebo.notificationapp"

        val notificationManager : NotificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val id = Random.nextInt(1,100)

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0,intent,0)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificationChannel = NotificationChannel(channelId,message, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = NotificationCompat.Builder(this, channelId)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setAutoCancel(true)
                .setNotificationSilent()
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
//                .setContentText(message)
                .setStyle(NotificationCompat.BigTextStyle()
                    .bigText(message)
                    .setSummaryText("Summary")
                    .setBigContentTitle("Big Text")
                )
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
        }else{
            builder = NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setNotificationSilent()
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
        }

        notificationManager.notify(id,builder.build())
    }
}