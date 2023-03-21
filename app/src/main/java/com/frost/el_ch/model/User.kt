package com.frost.el_ch.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int? = 0,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "saldo") val saldo: Float,
    @ColumnInfo(name = "tarjetas") var cards: String? = null
)
