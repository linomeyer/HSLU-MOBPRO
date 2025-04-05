package ch.hslu.demoproject.ui.sms

import android.content.Context
import android.provider.Telephony
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ch.hslu.demoproject.Screen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

class SmsScreen {
    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun Show(navController: NavHostController) {
        val smsPermissionState = rememberPermissionState(
            android.Manifest.permission.READ_SMS
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            val smsList: MutableList<String> = remember { mutableStateListOf() }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                val context = LocalContext.current
                Button(
                    onClick = {
                        if (!smsPermissionState.status.isGranted) {
                            smsPermissionState.launchPermissionRequest()
                        }
                        if (smsPermissionState.status.isGranted) {
                            smsList.removeAll(smsList)
                            smsList.addAll(readSms(context))
                        }
                    }
                ) {
                    Text("Read SMS")
                }
            }

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(smsList.size) { index ->
                    Text(smsList[index])
                }
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

    private fun readSms(context: Context): List<String> {
        val smsList = mutableListOf<String>()
        context.contentResolver.query(
            Telephony.Sms.Inbox.CONTENT_URI,
            arrayOf(Telephony.Sms.Inbox._ID, Telephony.Sms.Inbox.BODY), // projection
            null, // selection
            null, // selection args
            null // sort order
        )?.let { cursor ->
            val bodyIndex = cursor.getColumnIndex(Telephony.Sms.BODY)
            while (cursor.moveToNext()) {
                val body = cursor.getString(bodyIndex)
                smsList.add(body)
            }
            cursor.close()
        }
        return smsList
    }
}