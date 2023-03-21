package com.frost.el_ch.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.frost.el_ch.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM usuarios")
    suspend fun getAllUsers():List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

}