package ch.hslu.demoproject.ui.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import ch.hslu.demoproject.Screen
import ch.hslu.demoproject.business.users.data.User

class UserScreen {
    @Composable
    fun Show(navController: NavHostController) {
        val userViewModel = getUserViewModel()
        val user by userViewModel.user.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Welcome to the UserScreen!",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary,
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                UserForm(userViewModel)
            }

            Column(modifier = Modifier.padding(16.dp)) {
                DisplayActiveUser(user)
            }

            Column(modifier = Modifier
                .padding(16.dp)
                .weight(1f)) {
                DisplayUsersInDB(userViewModel)
            }

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(onClick = { navController.navigate(Screen.Home.name) }) {
                    Text("To Home")
                }
            }
        }
    }

    @Composable
    private fun DisplayUsersInDB(userViewModel: UserViewModel) {
        Text(
            text = "Saved Users",
            style = MaterialTheme.typography.titleMedium,
            textDecoration = TextDecoration.Underline
        )

        val users: List<User> = userViewModel.loadAllUsers().collectAsState().value
        LazyColumn {
            items(count = users.size) { index ->
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = "${users[index].name}, ${users[index].age}, ${users[index].authorized}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }

    @Composable
    private fun DisplayActiveUser(user: User) {
        Text(
            text = "Active User",
            style = MaterialTheme.typography.titleMedium,
            textDecoration = TextDecoration.Underline
        )

        val authorized = if (user.authorized) "Authorized" else "Unauthorized"
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = "${user.name}, ${user.age}, $authorized",
            style = MaterialTheme.typography.bodyMedium
        )
    }

    @Composable
    private fun UserForm(userViewModel: UserViewModel) {
        var name by remember { mutableStateOf("") }
        var age by remember { mutableIntStateOf(0) }
        var isActive by remember { mutableStateOf(false) }

        Column(horizontalAlignment = Alignment.Start) {
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") }
            )
            TextField(
                modifier = Modifier.padding(top = 12.dp),
                value = age.toString(),
                onValueChange = { age = it.toInt() },
                label = { Text("Age") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Switch(checked = isActive, onCheckedChange = { isActive = it })
                Text(modifier = Modifier.padding(horizontal = 16.dp), text = "Authorize User")
            }
            Button(onClick = { userViewModel.updateUser(User(name, age, isActive)) }) {
                Text("Save")
            }
            Button(onClick = { userViewModel.addUser(User(name, age, isActive)) }) {
                Text("Add User")
            }
        }
    }

    @Composable
    private fun getUserViewModel(): UserViewModel {
        return viewModel(
            factory = UserViewModelFactory(LocalContext.current)
        )
    }
}

