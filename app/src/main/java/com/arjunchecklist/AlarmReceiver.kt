package com.arjunchecklist

import android.R
import android.app.PendingIntent
import android.content.Intent
import android.content.Context.NOTIFICATION_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.widget.Toast
import androidx.core.app.NotificationCompat


class AlarmReceiver : BroadcastReceiver() {

    private var mNotificationManager: NotificationManager? = null

    /**
     * Called when the BroadcastReceiver receives an Intent broadcast.
     *
     * @param context The Context in which the receiver is running.
     * @param intent The Intent being received.
     */



    override fun onReceive(context: Context, intent: Intent) {


        mNotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Deliver the notification.
        deliverNotification(context)
        //Toast.makeText(context, mystr, Toast.LENGTH_SHORT).show();

    }


    /**
     * Builds and delivers the notification.
     *
     * @param context, activity context.
     */
    private fun deliverNotification(context: Context) {
        // Create the content intent for the notification, which launches
        // this activity
        val contentIntent = Intent(context, MainActivity::class.java)

        val contentPendingIntent = PendingIntent.getActivity(
            context, NOTIFICATION_ID, contentIntent, PendingIntent
                .FLAG_UPDATE_CURRENT
        )
        // Build the notification
        val builder = NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_lock_idle_alarm)
            .setContentTitle("You have a checklist")
            .setContentText("dd")
            .setContentIntent(contentPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)

        // Deliver the notification
        mNotificationManager!!.notify(NOTIFICATION_ID, builder.build())
    }

    companion object {
        // Notification ID.
        private val NOTIFICATION_ID = 0
        // Notification channel ID.
        private val PRIMARY_CHANNEL_ID = "primary_notification_channel"
    }
}