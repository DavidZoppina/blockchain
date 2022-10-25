package com.andtv.flicknplay.workbrowse.data.repository

import com.andtv.flicknplay.workbrowse.data.remote.datasource.AccountSettingRemoteDataSource
import com.andtv.flicknplay.workbrowse.data.remote.datasource.AccountSettingsDataSource
import com.andtv.flicknplay.workbrowse.data.remote.model.SocialLoginModel
import com.andtv.flicknplay.workbrowse.data.remote.model.accountSettingDetail.AccountDetailRequest
import com.andtv.flicknplay.workbrowse.data.remote.model.accountSettingDetail.ResetPasswordRequest
import javax.inject.Inject

class AccountSettingDetailRepository @Inject constructor(
    private val accountSettingRemoteDataSource: AccountSettingRemoteDataSource
){
    fun getAccountSettingDetail(id:Int) = accountSettingRemoteDataSource.getAccountSettingDetail(id)
    fun socialConnection(provider:String,action:String,request : SocialLoginModel) = accountSettingRemoteDataSource.socialConnection(provider,action,request)
    fun updateUserProfile(id:String,request: AccountDetailRequest) = accountSettingRemoteDataSource.updateUserProfile(id,request)
    fun getAllSocialProfile(request : SocialLoginModel) = accountSettingRemoteDataSource.getAllSocialProfile(request)
    fun updateCredentials(request: ResetPasswordRequest) =accountSettingRemoteDataSource.updateCredentials(request)

}