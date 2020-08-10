package br.com.brunamarcal.tmdbproject.data.database.modeldb

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertUser(user: User)

    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    fun selectedUser (email: String, password: String): LiveData<User>

    @Query("SELECT * FROM user WHERE email = :email")
    fun selectedEmail(email: String): LiveData<User>

    @Delete
    suspend fun deleteUser(user: User)
}