package com.angeloplacebo.notificationapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.text.HtmlCompat
import java.util.*
import kotlin.random.Random
import kotlin.random.Random.Default.nextInt

class MainActivity : AppCompatActivity() {
    private lateinit var btNotificar : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btNotificar=findViewById(R.id.btNoficar)

        btNotificar.setOnClickListener{criarNotificacao()}

    }


    fun criarNotificacao(){
        val titulo = HtmlCompat.fromHtml("<strong> Titulo da notificação</strong>",HtmlCompat.FROM_HTML_MODE_LEGACY)
        val descricao = "Descricao da notificação"
        val corpo = "Corpo da notificação"

        val notificationChannel : NotificationChannel
        val builder : NotificationCompat.Builder
        val channelId = "com.angeloplacebo.notificationapp"

        val notificationManager : NotificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val id = Random.nextInt(1,100)

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0,intent,0)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificationChannel = NotificationChannel(channelId,descricao,NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = NotificationCompat.Builder(this, channelId)
                    .setAutoCancel(true)
                    .setNotificationSilent()
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle(titulo)
                    .setContentText(corpo)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
        }else{
            builder = NotificationCompat.Builder(this)
                    .setAutoCancel(true)
                    .setNotificationSilent()
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle(titulo)
                    .setContentText(corpo)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
        }

        notificationManager.notify(id,builder.build())
    }

}