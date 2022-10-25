package com.andtv.flicknplay.workbrowse.presentation.ui.fragment

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.andtv.flicknplay.workbrowse.R
import com.andtv.flicknplay.workbrowse.data.remote.api.UserApi
import com.andtv.flicknplay.workbrowse.data.remote.model.SocialLoginModel
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.*
import twitter4j.auth.AccessToken
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class FacebookLoginSDK(activity: Activity) {

    var loginUsing = false
    var sharedPreferences: SharedPreferences? =null
    companion object{
        val SHAREPREF_KEY="SHAREPREF_KEY"
        val ACCESS_TOKEN_KEY="ACCESS_TOKEN_KEY"
    }

    init {
        sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE)
    }

    fun getAccessToken():String?{
        val token = sharedPreferences?.getString(ACCESS_TOKEN_KEY,"")
        Log.e("getAccessToken",token.toString())
        return token
    }

    fun setAccessToken(user_access_token:String){
        Log.e("setAccessToken",user_access_token.toString())
       val sharedEdit= sharedPreferences?.edit()
        sharedEdit?.putString(ACCESS_TOKEN_KEY, user_access_token)
        sharedEdit?.apply()
    }

    interface FacebookLoginResponse{
        fun genrateTokeResponse(firstCall:FirstCall)
        fun getUserAccessToken(secondCall: SecondCall)
        fun getUserInformation(thirdCall: ThirdCall,user_access_token:String)

    }

    var facebookLoginResponse:FacebookLoginResponse? = null


    data class FacebookError(
        @SerializedName("code")
        var code:Int? = null,
        @SerializedName("message")
        var message:String? = null,
        @SerializedName("type")
        var type:String? = null,
        @SerializedName("error_user_title")
        var error_user_title:String? = null,
        @SerializedName("error_user_msg")
        var error_user_msg:String? = null,
        @SerializedName("is_transient")
        var is_transient:Boolean? = null,
        @SerializedName("verification_uri")
        var verification_uri:String? = null,
        @SerializedName("error_subcode")
        var error_subcode:Int? = null,

        )

    data class FirstCall(
        @SerializedName("code")
        var code:String? = null,
        @SerializedName("user_code")
        var user_code:String? = null,
        @SerializedName("verification_uri")
        var verification_uri:String? = null,
        @SerializedName("expires_in")
        var expires_in:Int? = null,
        @SerializedName("interval")
        var interval:Int? = null,
    )

    data class SecondCall(
        @SerializedName("access_token")
        var access_token:String? = null,
        @SerializedName("data_access_expiration_time")
        var data_access_expiration_time:Int? = null,
        @SerializedName("expires_in")
        var expires_in:Int? = null,
        @SerializedName("error")
        var error:FacebookError? = null,
    )

    data class FacebookPictureData(
        @SerializedName("height")
        var height:Int? = null,
        @SerializedName("is_silhouette")
        var is_silhouette:Boolean? = null,
        @SerializedName("url")
        var url:String? = null,
        @SerializedName("width")
        var width:Int? = null,
    )
    data class FacebookPicture(
        @SerializedName("data")
        var data:FacebookPictureData? = null,
    )
    data class ThirdCall(
        @SerializedName("name")
        var name:String? = null,
        @SerializedName("email")
        var email:String? = null,
        @SerializedName("id")
        var id:String? = null,
        @SerializedName("picture")
        var picture:FacebookPicture? = null,
    )

    fun login(){
        val access_token = getAccessToken()

        if(!access_token.isNullOrEmpty()){
            loginUsing = true
            fetchUserInformation(access_token)
        }else{
            genrateToken()
        }
    }

    fun logout(){
        setAccessToken("")
    }

    fun genrateToken() {
        GlobalScope.launch(Dispatchers.IO) {
            val urlString = "https://graph.facebook.com/v2.6/device/login?access_token=802985104017922|ed57b3ed3e19cd6ee2ca4340ed3cb532&redirect_uri=https://www.flicknplay.com/secure/auth/social/facebook/callback"
            val url = URL(urlString)
            val httpsURLConnection = url.openConnection() as HttpsURLConnection
            httpsURLConnection.requestMethod = "POST"
            httpsURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded") // The format of the content we're sending to the server
            httpsURLConnection.setRequestProperty("Accept", "application/json") // The format of response we want to get from the server
            httpsURLConnection.doInput = true
            httpsURLConnection.doOutput = true

            val responseCode = httpsURLConnection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val response = httpsURLConnection.inputStream.bufferedReader()
                    .use { it.readText() }  // defaults to UTF-8
                withContext(Dispatchers.Main) {
                    // Convert raw JSON to pretty JSON using GSON library
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(JsonParser.parseString(response))
                    val firstCall  = gson.fromJson(prettyJson,FirstCall::class.java)

                    facebookLoginResponse?.genrateTokeResponse(firstCall)
                    delay(5000L)
                    checkForLoginStatus(firstCall.code.toString(),0)

                    Log.d("First Call :", prettyJson)

                }
            } else {
                Log.e("First Call Error", responseCode.toString())
            }
        }

    }

    fun checkForLoginStatus(code:String,count:Int){
        if(count >10){
            return
        }

        GlobalScope.launch(Dispatchers.IO) {
            val urlString = "https://graph.facebook.com/v2.6/device/login_status?access_token=802985104017922|ed57b3ed3e19cd6ee2ca4340ed3cb532&code=" + code
            val url = URL(urlString)
            val httpsURLConnection = url.openConnection() as HttpsURLConnection
            httpsURLConnection.requestMethod = "POST"
            httpsURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded") // The format of the content we're sending to the server
            httpsURLConnection.setRequestProperty("Accept", "application/json") // The format of response we want to get from the server
            httpsURLConnection.doInput = true
            httpsURLConnection.doOutput = true

            val responseCode = httpsURLConnection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val response = httpsURLConnection.inputStream.bufferedReader()
                    .use { it.readText() }  // defaults to UTF-8
                withContext(Dispatchers.Main) {
                    // Convert raw JSON to pretty JSON using GSON library
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(JsonParser.parseString(response))
                    val secondCall  = gson.fromJson(prettyJson,SecondCall::class.java)

                    if(secondCall.access_token != null ){
                        setAccessToken(secondCall.access_token!!)
                        facebookLoginResponse?.getUserAccessToken(secondCall)
                        fetchUserInformation(secondCall.access_token!!)

                    }else{
                        delay(5000L)
                        checkForLoginStatus(code,count+1)
                    }


                    Log.d("Second Call :", prettyJson)

                }
            } else {
                Log.e("Second Call Erorr", responseCode.toString())
                delay(5000L)
                checkForLoginStatus(code,count+1)
            }
        }
    }

    fun fetchUserInformation(user_access_token:String){
        GlobalScope.launch(Dispatchers.IO) {
            val urlString = "https://graph.facebook.com/v2.3/me?fields=name,email,picture&access_token=" + user_access_token
            val url = URL(urlString)
            val httpsURLConnection = url.openConnection() as HttpsURLConnection
            httpsURLConnection.requestMethod = "POST"
            httpsURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded") // The format of the content we're sending to the server
            httpsURLConnection.setRequestProperty("Accept", "application/json") // The format of response we want to get from the server
            httpsURLConnection.doInput = true
            httpsURLConnection.doOutput = true

            val responseCode = httpsURLConnection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val response = httpsURLConnection.inputStream.bufferedReader()
                    .use { it.readText() }  // defaults to UTF-8
                withContext(Dispatchers.Main) {
                    // Convert raw JSON to pretty JSON using GSON library
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(JsonParser.parseString(response))
                    val thirdCall  = gson.fromJson(prettyJson,ThirdCall::class.java)
                    Log.d("Third Call :", prettyJson)


                    facebookLoginResponse?.getUserInformation(thirdCall, user_access_token)


                }
            } else {
                if(loginUsing){
                    genrateToken()
                }
                Log.e("Third Call Erorr", responseCode.toString())
            }
        }
    }

}
