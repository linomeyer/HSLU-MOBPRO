package ch.hslu.demoproject.ui.user

import ch.hslu.demoproject.data.users.User
import ch.hslu.demoproject.data.users.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify


@OptIn(ExperimentalCoroutinesApi::class)
class UserViewModelTest {
    @Test
    fun testUpdateUser() = runTest {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        val userRepositoryMock = mock<UserRepository>()
        val user = User(
            name = "Hans Muster",
            age = 35,
            authorized = false
        )
        val viewModel = UserViewModel(userRepositoryMock)

        viewModel.updateUser(user)

        verify(userRepositoryMock).setName(user.name)
        verify(userRepositoryMock).setAge(user.age)
        verify(userRepositoryMock).setAuthorized(user.authorized)

        Dispatchers.resetMain()
    }

}