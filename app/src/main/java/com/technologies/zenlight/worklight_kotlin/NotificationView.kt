package com.technologies.zenlight.worklight_kotlin

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.app.NotificationChannel
import android.graphics.Color


const val NOTIFICATION_ID = "my_channel_01"

    fun showNotification(context: Context) {

        val resources = context.resources

        val intent = Intent(context, CloseActivity::class.java)
        intent.putExtra("notification","finish")

        val pi = PendingIntent.getActivity(context, 0, intent, 0)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val notificationChannel = NotificationChannel(NOTIFICATION_ID, "My Notifications", NotificationManager.IMPORTANCE_DEFAULT)
            notificationChannel.description = "Channel description"
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(false)
            notificationChannel.setSound(null,null)
            notificationManager.createNotificationChannel(notificationChannel)
        }


            val notification = NotificationCompat.Builder(context,NOTIFICATION_ID)
                .setTicker(resources.getString(R.string.on_notification))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("WorkLight is running")
                .setContentText("Touch to close app")
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build()
        //
        notificationManager.notify(1, notification)


    }
