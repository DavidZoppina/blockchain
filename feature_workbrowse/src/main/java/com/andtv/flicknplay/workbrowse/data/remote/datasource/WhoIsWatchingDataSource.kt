package com.andtv.flicknplay.workbrowse.data.remote.datasource

import com.andtv.flicknplay.workbrowse.data.remote.api.WhoIsWatchingApi
import com.andtv.flicknplay.workbrowse.data.remote.model.CreateUserProfileRequestModel
import com.andtv.flicknplay.workbrowse.data.remote.model.UpdateUserProfileRequestModel
import com.andtv.flicknplay.workbrowse.data.remote.model.WhoIsWatchingConfirmPasswordRequestModel
import javax.inject.Inject

class WhoIsWatchingDataSource @Inject constructor(
    private val whoIsWatchingApi: WhoIsWatchingApi
) {

    fun getAllAccountUsers(user_id: Int) =
        whoIsWatchingApi.getAllUsers(user_id)

    fun confirmPassword(id:String, password: String) = whoIsWatchingApi.confirmPassword(
        WhoIsWatchingConfirmPasswordRequestModel(id, password)
    )

    fun createUserProfile(
        avatar: String,
        kids: Boolean,
        name: String,
        password: String,
        rating:String) = whoIsWatchingApi.createUserProfile(
        CreateUserProfileRequestModel("storage/profile_avatars/${avatar}.png", kids, name, password, rating)
        )
    fun updateUserProfile(
        id: String,
        avatar: String,
        kids: Boolean,
        name: String,
        password: String,
        rating:String) = whoIsWatchingApi.updateUserProfile(
        UpdateUserProfileRequestModel(id, "storage/profile_avatars/${avatar}.png", kids, name, password, rating)
        )
}