package ch.hslu.demoproject

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

class DetailScreen {
    @Composable
    fun Show(fromScreen: String, screenNr: Int, navController: NavHostController) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.SpaceBetween,
                content = texts()
            )
            Column(
                Modifier.padding(top = 16.dp),
                content = homeScreenNavigation(screenNr, fromScreen, navController)
            )
        }
    }

    private fun texts(): @Composable (ColumnScope.() -> Unit) = {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Oben Anfang")
            Text("Oben Ende")
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text("Oben Mitte")
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Mitte Anfang")
            Text("Mitte Ende")
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text("Unten Mitte")
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Unten Anfang")
            Text("Unten Ende")
        }
    }

    private fun homeScreenNavigation(
        screenNr: Int,
        fromScreen: String,
        navController: NavHostController
    ): @Composable (ColumnScope.() -> Unit) = {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Detail Screen Nr. $screenNr | Von $fromScreen",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.tertiary,
            )
            Button(
                onClick = {
                    navController.navigate(Screen.Home.name)
                }
            ) {
                Text("To Home")
            }
        }
    }
}
