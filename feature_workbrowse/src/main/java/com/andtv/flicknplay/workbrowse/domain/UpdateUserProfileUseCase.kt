package com.andtv.flicknplay.workbrowse.domain

import com.andtv.flicknplay.workbrowse.data.repository.WhoIsWatchingRepository
import javax.inject.Inject

class UpdateUserProfileUseCase  @Inject constructor(
    private val whoIsWatchingRepository: WhoIsWatchingRepository
) {
    operator fun invoke (
        id: String,
        avatar: String,
        kids: Boolean,
        name: String,
        password: String,
        rating:String) = whoIsWatchingRepository
        .updateUserProfile(
            id,
            avatar,
            kids,
            name,
            password,
            rating
        )
}