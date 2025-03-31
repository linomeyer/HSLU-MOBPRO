package ch.hslu.demoproject.business.musicplayer

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.core.app.ServiceCompat

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
        return SONG_LIST[++songsPlayed % SONG_LIST.size]
    }

    private fun startForegroundService() {
        ServiceCompat.startForeground(this, NOTIFICATION_ID)
    }

    inner class MusicPlayerApiImpl : MusicPlayerApi, Binder() {
        override fun next(): String {
            return this@MusicPlayerService.next()
        }
    }

    companion object {
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