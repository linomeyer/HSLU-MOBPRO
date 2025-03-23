package ch.hslu.demoproject.business.users

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import ch.hslu.demoproject.business.users.data.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(entity = UserEntity::class)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM user")
    @RewriteQueriesToDropUnusedColumns
    fun loadAllUsers(): Flow<List<User>>
}
