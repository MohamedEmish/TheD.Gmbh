package com.example.thedgmbh.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

object SaveSharedPreference {
    private const val PREF_LOCALE_DB_EXIST = "db"

    private fun getSharedPreferences(ctx: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(ctx)
    }


    fun setDb(ctx: Context, isExist: Boolean) {
        val editor = getSharedPreferences(ctx).edit()
        editor.putBoolean(PREF_LOCALE_DB_EXIST, isExist)
        editor.apply()
    }

    fun getUDb(ctx: Context): Boolean {
        return getSharedPreferences(ctx).getBoolean(PREF_LOCALE_DB_EXIST,false )
    }
}