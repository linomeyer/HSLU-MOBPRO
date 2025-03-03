package com.example.demoproject

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
            verticalArrangement = Arrangement.SpaceAround,

            ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = "Welcome to Home Screen!",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
            )
            DetailScreenNavigation(navController)
        }
    }

    @Composable
    private fun DetailScreenNavigation(navController: NavHostController) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(
                onClick = {
                    navController.navigate("${Screen.Detail.name}/HomeScreen/1")
                }
            ) {
                Text("To Detail")
            }
            Text(
                modifier = Modifier.padding(8.dp),
                text = "With the button above you can go to the Detail Screen.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}