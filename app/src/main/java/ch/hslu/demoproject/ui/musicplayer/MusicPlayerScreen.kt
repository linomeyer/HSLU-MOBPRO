package ch.hslu.demoproject.ui.musicplayer

import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
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
import androidx.navigation.NavHostController
import ch.hslu.demoproject.Screen
import ch.hslu.demoproject.business.musicplayer.MusicPlayerService
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

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
        val context = LocalContext.current
        val intent = Intent(context, MusicPlayerService::class.java)

        var musicPlayerConnection: MusicPlayerConnection? by remember { mutableStateOf(null) }
        val songHistory: MutableList<String> = remember { mutableStateListOf() }

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
                            context.startService(intent)
                            musicPlayerConnection = MusicPlayerConnection().also {
                                context.bindService(
                                    intent,
                                    it,
                                    Context.BIND_AUTO_CREATE
                                )
                            }
                        }
                    },
                ) {
                    Text("Start")
                }

                Button(
                    onClick = {
                        context.unbindService(musicPlayerConnection as ServiceConnection)
                        context.stopService(intent)
                    },
                ) {
                    Text("Stop")
                }

                Button(
                    onClick = {
                        val currentSong = musicPlayerConnection?.getMusicPlayer()?.next()
                        currentSong?.let { songHistory.add(it) }
                    }
                ) {
                    Text("Next")
                }
            }

            ShowSongHistory(songHistory)
        }
    }

    @Composable
    private fun ShowSongHistory(songHistory: MutableList<String>) {
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

            LazyColumn {
                items(songHistory.size) { index ->
                    ShowSongHistoryEntry(index, songHistory[index])
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
}