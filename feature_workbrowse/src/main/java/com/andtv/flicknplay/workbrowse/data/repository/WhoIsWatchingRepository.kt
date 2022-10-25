package com.andtv.flicknplay.workbrowse.data.repository

import com.andtv.flicknplay.workbrowse.data.remote.datasource.WhoIsWatchingDataSource
import com.andtv.flicknplay.workbrowse.data.remote.model.toDomainModel
import javax.inject.Inject

class WhoIsWatchingRepository @Inject constructor(
    private val whoIsWatchingDataSource: WhoIsWatchingDataSource
){
    fun getAllAccountUsers (userId: Int) =
        whoIsWatchingDataSource.getAllAccountUsers(userId)
            .map {
               it.toDomainModel()
            }

    fun confirmPassword(id: String, password: String) = whoIsWatchingDataSource.confirmPassword(id, password)

    fun createUserProfile(
        avatar: String,
        kids: Boolean,
        name: String,
        password: String,
        rating:String) = whoIsWatchingDataSource
                            .createUserProfile(
                                avatar,
                                kids,
                                name,
                                password,
                                rating
                            )

    fun updateUserProfile(
        id: String,
        avatar: String,
        kids: Boolean,
        name: String,
        password: String,
        rating:String) = whoIsWatchingDataSource
        .updateUserProfile(
            id,
            avatar,
            kids,
            name,
            password,
            rating
        )
}