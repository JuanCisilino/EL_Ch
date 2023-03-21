package com.frost.el_ch.extensions

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.Fragment
import com.frost.el_ch.R

fun Fragment.getPref(context: Context): SharedPreferences = context.getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)

fun Fragment.clearPrefs(context: Context){
    val prefs = getPref(context)
    prefs.edit()?.clear()?.apply()
}

fun Fragment.getEmailPref(context: Context): String?{
    val prefs = getPref(context)
    return prefs.getString(R.string.email.toString(), null)
}