package ch.hslu.demoproject.data.users

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(entity = User::class)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM user")
    @RewriteQueriesToDropUnusedColumns
    fun loadAllUsers(): Flow<List<User>>
}
