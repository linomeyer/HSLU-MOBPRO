package ch.hslu.demoproject.data

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ch.hslu.demoproject.data.users.User
import ch.hslu.demoproject.data.users.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor

@Database(
    entities = [User::class],
    version = 2,
    exportSchema = true,
    autoMigrations = [AutoMigration(from = 1, to = 2)]
)
abstract class DemoDB : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private const val DB_NAME = "demo-database"
        private var INSTANCE: DemoDB? = null

        fun getDatabase(context: Context): DemoDB {
            return INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context): DemoDB {
            val ioDispatcherExecutor = Dispatchers.IO.asExecutor()

            return Room.databaseBuilder(context, DemoDB::class.java, DB_NAME)
                .setQueryExecutor(ioDispatcherExecutor)
                .setTransactionExecutor(ioDispatcherExecutor)
                .build()
        }
    }
}