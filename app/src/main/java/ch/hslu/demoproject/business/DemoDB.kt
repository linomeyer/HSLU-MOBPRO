package ch.hslu.demoproject.business

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ch.hslu.demoproject.business.users.UserDao
import ch.hslu.demoproject.business.users.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
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