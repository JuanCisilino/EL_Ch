package com.frost.el_ch.repositories

import com.frost.el_ch.database.UserDao
import com.frost.el_ch.model.User
import javax.inject.Inject

class UserRepository @Inject constructor(private val dao: UserDao) {

    suspend fun getAllUsers() = dao.getAllUsers()

    suspend fun insertUser(user: User) = dao.insertUser(user)

}