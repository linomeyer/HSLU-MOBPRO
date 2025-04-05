package ch.hslu.demoproject.business

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class MyManifestBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "ch.hslu.demoproject.MY_ACTION") {
            Log.d("MyManifestBroadcastReceiver", "Broadcast received")
        }
    }
}