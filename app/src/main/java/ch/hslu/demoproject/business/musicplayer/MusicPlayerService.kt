package ch.hslu.demoproject.business.musicplayer

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.graphics.BitmapFactory
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import com.example.demoproject.R

class MusicPlayerService : Service() {
    private val musicPlayerApi = MusicPlayerApiImpl()
    private var songsPlayed = -1

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForegroundService()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder {
        return musicPlayerApi
    }

    fun next(): String {
        songsPlayed++
        createNotification()
        return SONG_LIST[songsPlayed % SONG_LIST.size]
    }

    private fun startForegroundService() {
        createNotificationChannel()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ServiceCompat.startForeground(this, NOTIFICATION_ID, createNotification(), ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK)
        } else {
            ServiceCompat.startForeground(this, NOTIFICATION_ID, createNotification(), 0)
        }
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL,
            "Music notifications",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun createNotification(): Notification {
        val currentSong: String
        if (songsPlayed < 0) {
            currentSong = "-- No Song Playing --"
        } else {
            currentSong = SONG_LIST[songsPlayed % SONG_LIST.size]
        }

        val notification = NotificationCompat
            .Builder(this, NOTIFICATION_CHANNEL)
            .setContentTitle(getString(R.string.musicplayer_desc))
            .setContentText(currentSong)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setOngoing(true)
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    resources,
                    android.R.drawable.ic_media_play
                )
            )
            .setWhen(System.currentTimeMillis())
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notification)
        return notification

    }

    inner class MusicPlayerApiImpl : MusicPlayerApi, Binder() {
        override fun next(): String {
            return this@MusicPlayerService.next()
        }
    }

    companion object {
        private const val NOTIFICATION_CHANNEL = "ch.hslu.mobpro.demo.channel"
        private const val NOTIFICATION_ID = 1
        private val SONG_LIST =
            listOf(
                "The Chain - Fleetwood Mac",
                "Drive - R.E.M.",
                "Where Is My Mind? - Pixies",
                "Road Trippin' - RHCP",
                "Message In A Bottle - The Police"
            )
    }
}