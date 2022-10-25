

package com.andtv.flicknplay.recommendation.data.local.provider.row

import android.app.Application
import android.app.NotificationManager
import androidx.recommendation.app.ContentRecommendation
import com.bumptech.glide.Glide
import com.andtv.flicknplay.model.domain.WorkDomainModel
import com.andtv.flicknplay.model.presentation.mapper.toViewModel
import com.andtv.flicknplay.recommendation.R
import com.andtv.flicknplay.recommendation.data.local.provider.RecommendationProvider
import com.andtv.flicknplay.route.workdetail.WorkDetailsRoute
import io.reactivex.Completable

/**
 *
 */
class RecommendationRowApi constructor(
    private val application: Application,
    private val notificationManager: NotificationManager,
    private val workDetailsRoute: WorkDetailsRoute
) : RecommendationProvider {

    override fun loadRecommendations(works: List<WorkDomainModel>?): Completable =
            Completable.create {
                notificationManager.cancelAll()

                works?.map { work -> work.toViewModel() }
                        ?.forEach { workViewModel ->
                            val cardBitmap = Glide.with(application)
                                    .asBitmap()
                                    .load(workViewModel.posterUrl)
                                    .submit(application.resources.getDimensionPixelSize(R.dimen.movie_card_width),
                                            application.resources.getDimensionPixelSize(R.dimen.movie_card_height))
                                    .get()

                            val contentRecommendation = ContentRecommendation.Builder()
                                    .setAutoDismiss(true)
                                    .setIdTag(workViewModel.id.toString())
                                    .setGroup(application.getString(R.string.app_name))
                                    .setBadgeIcon(R.drawable.movie)
                                    .setTitle(workViewModel.title)
                                    .setContentImage(cardBitmap)
                                    .setContentTypes(arrayOf(ContentRecommendation.CONTENT_TYPE_MOVIE))
                                    .setBackgroundImageUri(workViewModel.backdropUrl)
                                    .setText(application.getString(R.string.popular))
                                    .setContentIntentData(ContentRecommendation.INTENT_TYPE_ACTIVITY,
                                            workDetailsRoute.buildWorkDetailRoute(workViewModel).intent.apply {
                                                // Ensure a unique PendingIntents, otherwise all
                                                // recommendations end up with the same PendingIntent
                                                action = workViewModel.id.toString()
                                            },
                                            0, null
                                    )
                                    .build()

                            notificationManager.notify(workViewModel.id, contentRecommendation.getNotificationObject(application))
                        }
                it.onComplete()
            }
}
