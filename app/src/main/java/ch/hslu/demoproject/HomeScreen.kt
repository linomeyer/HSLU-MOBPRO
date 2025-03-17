package ch.hslu.demoproject

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
            Row {
                DetailScreenNavigation(navController)
            }
        }
    }

    @Composable
    private fun DetailScreenNavigation(navController: NavHostController) {
        Row {
            Button(
                onClick = {
                    navController.navigate("${Screen.Detail.name}/HomeScreen/1")
                }
            ) {
                Text("To Detail")
            }
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
                .padding(vertical = 8.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ElectronicsScreenNavigation(navController)
            }
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