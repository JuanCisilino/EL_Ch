package com.frost.el_ch.extensions

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import com.frost.el_ch.R

fun showToast(context: Context, message: String){
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

fun Activity.showAlert(){
    val builder = AlertDialog.Builder(this)
    builder.setTitle(getString(R.string.error))
    builder.setMessage(getString(R.string.error_message))
    builder.setPositiveButton(getString(R.string.ok), null)
    val dialog = builder.create()
    dialog.show()
}

fun Activity.getPref(): SharedPreferences = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)

fun Activity.saveEmail(email: String){
    val prefs = getPref().edit()
    prefs.putString(R.string.email.toString(), email)
    prefs.apply()
}

fun Activity.getEmailPref(): String?{
    val prefs = getPref()
    return prefs.getString(R.string.email.toString(), null)
}

fun Activity.clearPrefs(){
    val prefs = getPref()
    prefs?.edit()?.clear()?.apply()
}