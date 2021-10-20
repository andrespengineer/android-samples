package com.social.data.local

import android.content.Context
import com.google.gson.Gson
import java.lang.reflect.Type

class PreferencesDataSource(private val applicationContext: Context) {

    fun <T> getData(database: String, identifier: String, type: Type): T? {

        val sharedPreferences = applicationContext.getSharedPreferences(database, Context.MODE_PRIVATE)

        return Gson().fromJson(sharedPreferences.getString(identifier, null), type)
        }

        fun <T> getAllData(database: String, type: Type): MutableList<T> {

        val sharedPreferences = applicationContext.getSharedPreferences(database, Context.MODE_PRIVATE)
        val results: MutableList<T> = ArrayList()

        sharedPreferences.all.keys.forEach {
            results.add(Gson().fromJson(sharedPreferences.getString(it, null), type))
        }

        return results
    }

    fun <T> saveData(database: String, identifier: String?, objToSave: T, type: Type) {
        if(identifier == null) return

        val sharedPreferences = applicationContext.getSharedPreferences(database, Context.MODE_PRIVATE)
        sharedPreferences.edit().apply { putString(identifier, Gson().toJson(objToSave, type)) }.apply()
    }

    fun clearData(database: String, identifier: String) {
        val sharedPreferences = applicationContext.getSharedPreferences(database, Context.MODE_PRIVATE)
        sharedPreferences.edit().apply { putString(identifier, null) }.apply()
    }
}

