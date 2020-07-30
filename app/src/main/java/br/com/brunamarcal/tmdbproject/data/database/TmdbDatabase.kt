package br.com.brunamarcal.tmdbproject.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import br.com.brunamarcal.tmdbproject.data.database.converter.Converters
import br.com.brunamarcal.tmdbproject.data.database.modeldb.FavoriteMovie
import br.com.brunamarcal.tmdbproject.data.database.modeldb.FavoriteMovieDao
import br.com.brunamarcal.tmdbproject.data.database.modeldb.User
import br.com.brunamarcal.tmdbproject.data.database.modeldb.UserDao

@Database(entities = [User::class, FavoriteMovie::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TmdbDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun favoriteMovieDao(): FavoriteMovieDao

    companion object {
        @Volatile
        private var INSTANCE: TmdbDatabase? = null

        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                //caso haja alguma atualizaçao no db
            }
        }

        fun getDb(context: Context): TmdbDatabase{
            val tempIntance = INSTANCE
            if (tempIntance != null){
                return tempIntance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TmdbDatabase::class.java, "tmdb_db")
                    .addMigrations(MIGRATION_1_2)
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}