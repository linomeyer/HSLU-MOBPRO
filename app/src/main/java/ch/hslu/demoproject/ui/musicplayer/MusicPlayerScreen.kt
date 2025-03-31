package ch.hslu.demoproject.ui.musicplayer

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.BitmapFactory
import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.navigation.NavHostController
import ch.hslu.demoproject.Screen
import ch.hslu.demoproject.business.musicplayer.MusicPlayerService
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

private const val NOTIFICATION_CHANNEL = "ch.hslu.mobpro.demo.channel"

class MusicPlayerScreen {
    @Composable
    fun Show(navController: NavHostController) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Here you can start/stop the Music Player",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(vertical = 8.dp)
            ) {
                MusicPlayer()
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.End
            ) {
                Button(onClick = { navController.navigate(Screen.Home.name) }) {
                    Text("Home")
                }
            }
        }
    }


    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    private fun MusicPlayer() {
        val notificationManager = LocalContext.current.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createChannel(notificationManager)

        val context = LocalContext.current
        var musicPlayerConnection: MusicPlayerConnection? by remember { mutableStateOf(null) }
        val songHistory: MutableList<String> = remember { mutableStateListOf() }
        var currentSong: String? = ""

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val notificationPermissionState = rememberPermissionState(
                android.Manifest.permission.POST_NOTIFICATIONS
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(
                    onClick = {
                        if (!notificationPermissionState.status.isGranted) {
                            notificationPermissionState.launchPermissionRequest()
                        } else {
                            val intent = Intent(context, MusicPlayerService::class.java)

                            context.startService(intent)

                            musicPlayerConnection = MusicPlayerConnection().also {
                                context.bindService(
                                    intent,
                                    it,
                                    Context.BIND_AUTO_CREATE
                                )
                            }
                            createNotification(context, notificationManager, currentSong)
                        }
                    },
                ) {
                    Text("Start")
                }

                Button(
                    onClick = {
                        context.unbindService(musicPlayerConnection as ServiceConnection)
                        val intent = Intent(context, MusicPlayerService::class.java)
                        context.stopService(intent)
                        notificationManager.cancel(1)
                    },
                ) {
                    Text("Stop")
                }

                Button(
                    onClick = {
                        currentSong = musicPlayerConnection?.getMusicPlayer()?.next()
                        createNotification(context, notificationManager, currentSong)
                        currentSong?.let { songHistory.add(it) }
                    }
                ) {
                    Text("Next")
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Song History",
                    style = MaterialTheme.typography.titleSmall,
                    textDecoration = TextDecoration.Underline
                )

                LazyColumn() {
                    items(songHistory.size) { index ->
                        ShowSongHistoryEntry(index, songHistory[index])
                    }
                }
            }
        }
    }

    @Composable
    private fun ShowSongHistoryEntry(index: Int, song: String) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(text = "${index + 1} - $song", style = MaterialTheme.typography.bodySmall)
        }
    }

    private fun createNotification(
        context: Context,
        notificationManager: NotificationManager,
        currentSong: String?
    ) {
        val notification = NotificationCompat
            .Builder(context, NOTIFICATION_CHANNEL)
            .setContentTitle("HSLU Music Player")
            .setContentText(currentSong)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setOngoing(true)
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    context.resources,
                    android.R.drawable.ic_media_play
                )
            )
            .setWhen(System.currentTimeMillis())
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()

        notificationManager.notify(1, notification)
    }

    private fun createChannel(notificationManager: NotificationManager) {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL,
            "Music notifications",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)
    }
}