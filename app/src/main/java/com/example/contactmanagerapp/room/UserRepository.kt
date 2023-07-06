package com.example.contactmanagerapp.room

class UserRepository(private val dao: UserDAO) {
    val users = dao.getAllUserInDB()

    suspend fun insert(users: User): Long {
        return dao.insertUser(users)
    }

    suspend fun delete(users: User): Int {
        return dao.deleteUser(users)
    }

    suspend fun update(users: User): Long {
        return dao.updateUser(users)
    }

    suspend fun deleteAll() {
        return dao.deleteALl()
    }

}