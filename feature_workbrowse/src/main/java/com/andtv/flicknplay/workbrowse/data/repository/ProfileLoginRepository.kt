package com.andtv.flicknplay.workbrowse.data.repository

import com.andtv.flicknplay.workbrowse.data.remote.datasource.LoginRemoteDataSource
import javax.inject.Inject

class ProfileLoginRepository @Inject constructor(
    private val loginRemoteDataSource: LoginRemoteDataSource
) {

    fun login(username:String, password:String) = loginRemoteDataSource.login(username,password)
}