package ch.hslu.demoproject.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ch.hslu.demoproject.Screen

class HomeScreen {
    @Composable
    fun Show(navController: NavHostController) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = "Welcome to Home Screen!",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
            )
            Column {
                ShowElectronicsScreen(navController)
            }
            Column {
                ShowUserScreen(navController)
            }
            Column {
                ShowMusicPlayerScreen(navController)
            }
            Row {
                DetailScreenNavigation(navController)
            }
        }
    }

    @Composable
    private fun ShowMusicPlayerScreen(navController: NavHostController) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Here you can go to the Music Player",
                style = MaterialTheme.typography.labelLarge
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = { navController.navigate(Screen.MusicPlayer.name) }) {
                Text("Music Player")
            }
        }
    }

    @Composable
    private fun ShowUserScreen(navController: NavHostController) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Here you can manage the users",
                style = MaterialTheme.typography.labelLarge
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            UserScreenNavigation(navController)
        }
    }

    @Composable
    private fun UserScreenNavigation(navController: NavHostController) {
        Button(onClick = { navController.navigate(Screen.User.name) }) {
            Text("Users")
        }
    }

    @Composable
    private fun DetailScreenNavigation(navController: NavHostController) {
        Button(
            onClick = {
                navController.navigate("${Screen.Detail.name}/HomeScreen/1")
            }
        ) {
            Text("Detail")
        }
    }

    @Composable
    private fun ShowElectronicsScreen(navController: NavHostController) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Here you can view the API Data",
                style = MaterialTheme.typography.labelLarge
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            ElectronicsScreenNavigation(navController)
        }
    }

    @Composable
    private fun ElectronicsScreenNavigation(navController: NavHostController) {
        Button(
            onClick = {
                navController.navigate(Screen.Electronics.name)
            }
        ) {
            Text("Electronics")
        }
    }

}