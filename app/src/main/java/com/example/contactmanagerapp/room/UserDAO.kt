package com.example.contactmanagerapp.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDAO {
    @Insert
    suspend fun insertUser(user: User): Long

    @Update
    suspend fun updateUser(user: User): Long

    @Delete
    suspend fun deleteUser(user: User) : Int

    @Query("DELETE FROM user")
    suspend fun deleteALl()

    @Query("SELECT *  FROM user")
    fun getAllUserInDB(): LiveData<List<User>>
}