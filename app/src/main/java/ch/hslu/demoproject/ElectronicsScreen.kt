package ch.hslu.demoproject

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ch.hslu.demoproject.api.electronics.ElectronicsViewModel
import ch.hslu.demoproject.api.electronics.data.Electronic

class ElectronicsScreen {
    @Composable
    fun Show(navController: NavController) {
        val electronics = loadElectronics()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            electronics.forEach { ShowElectronic(it) }
        }
    }

    @Composable
    private fun ShowElectronic(electronic: Electronic) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(text = electronic.name)
        }
    }

    @Composable
    private fun loadElectronics(): List<Electronic> {
        val electronicsViewModel: ElectronicsViewModel = viewModel();
        electronicsViewModel.requestElectronicsFromServer();
        return electronicsViewModel.electronicsFlow.collectAsState().value
    }
}