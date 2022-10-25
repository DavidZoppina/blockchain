package com.andtv.flicknplay.workbrowse.data.remote.api

import com.andtv.flicknplay.workbrowse.data.remote.model.AllSocialResponse
import com.andtv.flicknplay.workbrowse.data.remote.model.SocialLoginModel
import com.andtv.flicknplay.workbrowse.data.remote.model.SocialResponse
import com.andtv.flicknplay.workbrowse.data.remote.model.accountSettingDetail.AccountDetailRequest
import com.andtv.flicknplay.workbrowse.data.remote.model.accountSettingDetail.AccountSettingDetailResponse
import com.andtv.flicknplay.workbrowse.data.remote.model.accountSettingDetail.ResetPasswordRequest
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AccountSettingDetailApi {

    @GET("/api/v1/user-profile/{id}")
    fun getAccountSettingDetail(@Path ("id") id: Int) : Single<AccountSettingDetailResponse>
    @POST("auth/social/{provider}/{action}")
    fun socialConnection(@Path("provider") provider:String,@Path("action") action:String,@Body request : SocialLoginModel) : Single<AllSocialResponse>
    @POST("auth/user-profile-update/{id}")
    fun updateUserProfile(@Path("id") id:String,@Body request: AccountDetailRequest): Single<SocialResponse>
    @POST("auth/get-social-profile")
    fun getAllSocialProfile(@Body request: SocialLoginModel): Single<AllSocialResponse>

    @POST("auth/update-credentials")
    fun updateCredentials(@Body request: ResetPasswordRequest): Single<SocialResponse>

    @POST("auth/social/{provider}/disconnect-social-account")
    fun socialDisconnection(@Path("provider") provider:String,@Body request : SocialLoginModel) : Single<AllSocialResponse>


}

