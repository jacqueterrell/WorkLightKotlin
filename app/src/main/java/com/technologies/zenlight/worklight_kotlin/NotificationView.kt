package com.technologies.zenlight.worklight_kotlin

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat



    fun showNotification(context: Context) {

        val resources = context.resources

        val intent = Intent(context, CloseActivity::class.java)

        val pi = PendingIntent.getActivity(context, 0, intent, 0)


        val notification = NotificationCompat.Builder(context)
                .setTicker(resources.getString(R.string.on_notification))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("WorkLight is running")
                .setContentText("Touch to close app")
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build()
        //
        val notificationmanager = NotificationManagerCompat.from(context)
        notificationmanager.notify(0, notification)


    }
