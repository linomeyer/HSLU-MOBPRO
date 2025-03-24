package ch.hslu.demoproject.data.users

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import ch.hslu.demoproject.data.DemoDB
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepository(private val context: Context) {
    private val demoDB: DemoDB = DemoDB.getDatabase(context)

    val user: Flow<User> = context.dataStore.data.map {
        val name = it[PreferenceKeys.USER_NAME]
        val age = it[PreferenceKeys.USER_AGE]
        val isAuthorized = it[PreferenceKeys.USER_AUTHORIZED]
        User(
            name = name ?: "",
            age = age ?: -1,
            authorized = isAuthorized ?: false
        )
    }

    suspend fun setName(name: String) {
        context.dataStore.edit {
            it[PreferenceKeys.USER_NAME] = name
        }
    }

    suspend fun setAge(age: Int) {
        context.dataStore.edit {
            it[PreferenceKeys.USER_AGE] = age
        }
    }

    suspend fun setAuthorized(isAuthorized: Boolean) {
        context.dataStore.edit {
            it[PreferenceKeys.USER_AUTHORIZED] = isAuthorized
        }
    }

    suspend fun addUser(user: User) {
        demoDB.userDao().insertUser(user)
    }

    fun getAllUsers(): Flow<List<User>> {
        return demoDB.userDao().loadAllUsers()
    }

    companion object {
        private val Context.dataStore by preferencesDataStore(name = "user_preferences")
    }

    // singleton
    private object PreferenceKeys {
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_AGE = intPreferencesKey("user_age")
        val USER_AUTHORIZED = booleanPreferencesKey("user_authorized")
    }
}
