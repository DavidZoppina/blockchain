/*
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.andtv.flicknplay.recommendation.data.local.provider.channel

import android.app.Application
import android.content.ContentUris
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.tvprovider.media.tv.Channel
import androidx.tvprovider.media.tv.ChannelLogoUtils
import androidx.tvprovider.media.tv.PreviewProgram
import androidx.tvprovider.media.tv.TvContractCompat
import com.andtv.flicknplay.model.domain.WorkDomainModel
import com.andtv.flicknplay.model.presentation.mapper.toViewModel
import com.andtv.flicknplay.recommendation.R
import com.andtv.flicknplay.recommendation.data.local.provider.RecommendationProvider
import com.andtv.flicknplay.recommendation.data.local.sharedpreferences.LocalSettings
import com.andtv.flicknplay.route.workbrowse.WorkBrowseRoute
import com.andtv.flicknplay.route.workdetail.WorkDetailsRoute
import io.reactivex.Completable

/**
 *
 */
private const val CHANNEL_ID_KEY = "CHANNEL_ID_KEY"

class RecommendationChannelApi constructor(
    private val application: Application,
    private val localSettings: LocalSettings,
    private val workDetailsRoute: WorkDetailsRoute,
    private val workBrowseRoute: WorkBrowseRoute
) : RecommendationProvider {

    override fun loadRecommendations(works: List<WorkDomainModel>?): Completable =
            Completable.create {
                val channelId = getChannelId()

                works?.map { work -> work.toViewModel() }
                        ?.forEach { workViewModel ->
                            val programBuilder = PreviewProgram.Builder()
                                    .setChannelId(channelId)
                                    .setType(TvContractCompat.PreviewPrograms.TYPE_CLIP)
                                    .setTitle(workViewModel.title)
                                    .setDescription(workViewModel.overview)
                                    .setPosterArtUri(Uri.parse(workViewModel.backdropUrl))
                                    .setIntent(workDetailsRoute.buildWorkDetailRoute(workViewModel).intent)
                                    .setInternalProviderId(workViewModel.id.toString())

                            localSettings.getLongFromPersistence(workViewModel.id.toString(), 0L)
                                    .takeUnless { workId -> workId == 0L }
                                    ?.let {
                                        application.contentResolver.update(
                                                TvContractCompat.buildProgramUri(channelId),
                                                programBuilder.build().toContentValues(),
                                                null,
                                                null
                                        )
                                    }
                                    ?: run {
                                        application.contentResolver.insert(
                                                TvContractCompat.PreviewPrograms.CONTENT_URI,
                                                programBuilder.build().toContentValues()
                                        )?.let { programUri ->
                                            val programId = ContentUris.parseId(programUri)
                                            localSettings.applyLongToPersistence(workViewModel.id.toString(), programId)
                                        }
                                    }
                        }
                it.onComplete()
            }

    private fun getChannelId() =
            localSettings.getLongFromPersistence(CHANNEL_ID_KEY, 0L)
                    .takeUnless { it == 0L }
                    ?: run {
                        val channelBuilder = Channel.Builder()
                                .setType(TvContractCompat.Channels.TYPE_PREVIEW)
                                .setDisplayName(application.getString(R.string.popular))
                                .setAppLinkIntent(workBrowseRoute.buildWorkBrowseRoute().intent)

                        application.contentResolver.insert(
                                TvContractCompat.Channels.CONTENT_URI,
                                channelBuilder.build().toContentValues()
                        )?.let {
                            val channelId = ContentUris.parseId(it)

                            TvContractCompat.requestChannelBrowsable(
                                    application,
                                    channelId
                            )
                            ChannelLogoUtils.storeChannelLogo(
                                    application,
                                    channelId,
                                    BitmapFactory.decodeResource(application.resources, R.drawable.app_icon)
                            )

                            localSettings.applyLongToPersistence(CHANNEL_ID_KEY, channelId)
                            channelId
                        } ?: 0
                    }
}
