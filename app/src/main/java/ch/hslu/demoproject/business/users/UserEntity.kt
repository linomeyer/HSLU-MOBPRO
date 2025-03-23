package ch.hslu.demoproject.business.users

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("user")
class UserEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var name: String? = null
    var age: Int? = -1
    var authorized: Boolean? = false
}
