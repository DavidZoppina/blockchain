package com.andtv.flicknplay.workbrowse.data.remote.api

import com.andtv.flicknplay.workbrowse.data.remote.model.*
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface WhoIsWatchingApi {

    @GET("get-watchingprofile")
    fun getAllUsers(
        @Query("user_id") user_id:Int
    ): Single<List<WhoIsWatchingUserResponse>>


    @POST("confirm-password")
    fun confirmPassword(
      @Body request: WhoIsWatchingConfirmPasswordRequestModel
    ): Single<Object>

    @POST("create-profile")
    fun createUserProfile(
        @Body request: CreateUserProfileRequestModel
    ):Single<CreateUserProfileResponseModel>

    @POST("update-profile")
    fun updateUserProfile(
        @Body request: UpdateUserProfileRequestModel
    ):Single<UpdateProfileResponseModel>


}