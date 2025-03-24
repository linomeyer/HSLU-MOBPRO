package ch.hslu.demoproject.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.hslu.demoproject.data.users.User
import ch.hslu.demoproject.data.users.UserRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UserViewModel(private val userRepo: UserRepository) : ViewModel() {
    val user: StateFlow<User> = userRepo.user.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        User(name = "", age = -1, authorized = false)
    )

    fun updateUser(user: User) {
        viewModelScope.launch {
            userRepo.setName(user.name)
            userRepo.setAge(user.age)
            userRepo.setAuthorized(user.authorized)
        }
    }

    fun addUser(user: User) {
        viewModelScope.launch {
            userRepo.addUser(user)
        }
    }

    fun loadAllUsers(): StateFlow<List<User>> {
        return userRepo.getAllUsers().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }
}