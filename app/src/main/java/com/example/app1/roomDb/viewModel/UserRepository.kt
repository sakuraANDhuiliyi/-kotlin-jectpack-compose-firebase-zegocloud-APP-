package com.example.app1.roomDb.viewModel

import com.example.app1.roomDb.User
import com.example.app1.roomDb.UserDao

class UserRepository(private val userDao: UserDao) {

    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun getUser(username: String, password: String): User? {
        return userDao.getUser(username, password)
    }

    suspend fun checkUserExists(username: String): User? {
        return userDao.checkUserExists(username)
    }
}