package ch.hslu.demoproject.data.users

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("user")
class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String,
    var age: Int,
    var authorized: Boolean
)