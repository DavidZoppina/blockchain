package com.andtv.flicknplay.workbrowse.data.remote.datasource

import com.andtv.flicknplay.workbrowse.data.remote.api.AccountSettingDetailApi
import com.andtv.flicknplay.workbrowse.data.remote.api.AccountSettingsApi
import com.andtv.flicknplay.workbrowse.data.remote.model.SocialLoginModel
import com.andtv.flicknplay.workbrowse.data.remote.model.accountSettingDetail.AccountDetailRequest
import com.andtv.flicknplay.workbrowse.data.remote.model.accountSettingDetail.AccountSettingDetailResponse
import com.andtv.flicknplay.workbrowse.data.remote.model.accountSettingDetail.ResetPasswordRequest
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.Path
import javax.inject.Inject

class AccountSettingRemoteDataSource @Inject constructor(
    private val accountSettingDetailApi: AccountSettingDetailApi
) {

    fun getAccountSettingDetail(id:Int): Single<AccountSettingDetailResponse> {

//        val  a:Single<AccountSettingDetailResponse> = Single.error{
//          error("Server Error")
//        }
//        return a
       return accountSettingDetailApi.getAccountSettingDetail(id)
    }

    fun socialConnection(provider:String, action:String ,request : SocialLoginModel)=accountSettingDetailApi.socialConnection(provider,action,request)

    fun updateUserProfile(id:String,request: AccountDetailRequest) = accountSettingDetailApi.updateUserProfile(id,request)

    fun getAllSocialProfile(request : SocialLoginModel) = accountSettingDetailApi.getAllSocialProfile(request)

    fun updateCredentials(request: ResetPasswordRequest) =accountSettingDetailApi.updateCredentials(request)

}