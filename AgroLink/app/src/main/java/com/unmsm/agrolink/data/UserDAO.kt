//UserDAO.kt
package com.unmsm.agrolink.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User): Long

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    suspend fun login(username: String, password: String): User?
}
