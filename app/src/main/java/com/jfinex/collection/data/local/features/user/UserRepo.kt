package com.jfinex.collection.data.local.features.user

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepo @Inject constructor(private val dao: UserDao) {
    fun getUser(): Flow<User?> = dao.getUser()

    suspend fun setUser(user: User) = dao.addUser(user)
    suspend fun clear() = dao.clear()
}