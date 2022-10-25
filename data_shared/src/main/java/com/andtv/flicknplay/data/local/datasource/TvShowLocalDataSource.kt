

package com.andtv.flicknplay.data.local.datasource

import com.andtv.flicknplay.data.local.dao.TvShowDao
import com.andtv.flicknplay.model.data.local.TvShowDbModel
import javax.inject.Inject

/**
 *
 */
class TvShowLocalDataSource @Inject constructor(
    private val tvShowDao: TvShowDao
) {

    fun saveFavoriteTvShow(tvShowDbModel: TvShowDbModel) =
            tvShowDao.create(tvShowDbModel)

    fun deleteFavoriteTvShow(tvShowDbModel: TvShowDbModel) =
            tvShowDao.delete(tvShowDbModel)

    fun getTvShows() =
            tvShowDao.getAll()

    fun getById(tvShowDbModel: TvShowDbModel) =
            tvShowDao.getById(tvShowDbModel.id)
}
