package br.com.brunamarcal.tmdbproject.data.database.modeldb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    fun selectedUser (email: String, password: String): LiveData<User>

    @Query("SELECT * FROM user WHERE email = :email")
    fun selectedEmail(email: String): LiveData<User>
}