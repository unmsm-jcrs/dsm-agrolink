//CultivoDAO.kt

package com.unmsm.agrolink.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CultivoDao {
    @Insert
    suspend fun insertCultivo(cultivo: Cultivo): Long

    @Query("SELECT * FROM cultivos WHERE userId = :userId")
    suspend fun getCultivosByUser(userId: Int): List<Cultivo>
}
