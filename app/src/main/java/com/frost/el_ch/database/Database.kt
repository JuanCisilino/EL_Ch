package com.frost.el_ch.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.frost.el_ch.model.User

@Database(entities = [User::class], version = 2)
abstract class Database: RoomDatabase() {

    abstract fun getUserDao():UserDao
}