package ch.hslu.demoproject.ui.musicplayer

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import ch.hslu.demoproject.business.musicplayer.MusicPlayerApi

class MusicPlayerConnection : ServiceConnection {
    private var musicPlayerApi: MusicPlayerApi? = null

    override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
        musicPlayerApi = binder as MusicPlayerApi
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        musicPlayerApi = null
    }

    fun getMusicPlayer(): MusicPlayerApi? {
        return musicPlayerApi
    }
}