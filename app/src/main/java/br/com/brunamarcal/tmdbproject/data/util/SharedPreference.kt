package br.com.brunamarcal.tmdbproject.data.util

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(context: Context) {
    private val TMDB_SHARED = "TMDB_SHARED"
    private val sharedPref: SharedPreferences = context.getSharedPreferences(TMDB_SHARED, Context.MODE_PRIVATE)

    fun saveData(key: String, value: Long){
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    fun getData(key: String): Long? = sharedPref.getLong(key,0)

    fun savedImage(key: String, value: Int){
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putInt(key,value)
        editor.apply()
    }

    fun getSavedImage(key: String): Int? = sharedPref.getInt(key, 0)
}