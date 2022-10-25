package com.andtv.flicknplay.workbrowse.helper

import android.content.Context

import com.google.gson.Gson
import com.andtv.flicknplay.workbrowse.data.remote.model.login.LoginForm

/**
 * Created by olyanren on 24.01.2018.
 * SharedPreferencesUtil
 */
object SharedPreferencesHelper {
    fun writeLoginForm(context: Context, loginForm: LoginForm?) {
        write(context, "loginForm", loginForm)
    }

    fun writeAccessToken(context: Context, accessToken: String?) {
        write(context, "accessToken", accessToken)
    }

    fun writeUserId(context: Context, userId: Int?) {
        write(context, "userId", userId)
    }

    private fun write(context: Context, name: String?, `object`: Any?) {
        write(context, name, Gson().toJson(`object`))
    }

    private fun write(context: Context, name: String?, value: String?) {
        val preferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        preferences.edit().putString(name, value).apply()
    }


    fun readLoginForm(context: Context): LoginForm? {
        return read(context, "loginForm", LoginForm::class.java)
    }

    fun readAccessToken(context: Context): String? {
        return read(context, "accessToken", String::class.java)
    }


    fun readUserId(context: Context): Int? {
        return read(context, "userId", Int::class.java)
    }

    private fun <T> read(context: Context, name: String?, clazz: Class<T>?): T? {
        val value= read(context, name) ?: return null
        return Gson().fromJson(value, clazz)
    }

    private fun read(context: Context, name: String?): String? {

        val preferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        return preferences.getString(name, null)
    }


}