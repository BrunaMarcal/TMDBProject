package br.com.brunamarcal.tmdbproject.data.database.modeldb

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    fun selectedUser (email: String, password: String): LiveData<User>

    @Query("SELECT * FROM user WHERE id = :id")
    fun selectedId(id: Long): LiveData<User>

    @Delete
    suspend fun deleteUser(user: User)

    @Query("UPDATE user SET name = :name AND email = :email AND password = :password WHERE id = :id")
    suspend fun updateUser (id: Long, name: String, email: String, password: String)
}