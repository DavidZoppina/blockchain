

package com.andtv.flicknplay.castdetail.presenter

import androidx.leanback.widget.Presenter
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.only
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.andtv.flicknplay.castdetail.domain.GetCastDetailsUseCase
import com.andtv.flicknplay.castdetail.presentation.presenter.CastDetailsPresenter
import com.andtv.flicknplay.model.domain.CastDomainModel
import com.andtv.flicknplay.model.presentation.model.CastViewModel
import com.andtv.flicknplay.model.presentation.model.WorkType
import com.andtv.flicknplay.model.presentation.model.WorkViewModel
import com.andtv.flicknplay.presentation.scheduler.RxScheduler
import com.andtv.flicknplay.route.Route
import com.andtv.flicknplay.route.workdetail.WorkDetailsRoute
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Test

/**
 *
 */
private val CAST_VIEW_MODEL = CastViewModel(
        id = 1
)
private val CAST_DETAILED_DOMAIN_MODEL = CastDomainModel(
        id = 1,
        name = "Carlos",
        character = "Batman",
        birthday = "1990-07-13"
)
private val CAST_DETAILED_VIEW_MODEL = CastViewModel(
        id = 1,
        name = "Carlos",
        character = "Batman",
        birthday = "1990-07-13"
)
private val MOVIE_VIEW_MODEL = WorkViewModel(
        id = 1,
        title = "Batman",
        originalTitle = "Batman",
        type = WorkType.MOVIE
)

class CastDetailsPresenterTest {

    private val view: CastDetailsPresenter.View = mock()
    private val getCastDetailsUseCase: GetCastDetailsUseCase = mock()
    private val workDetailsRoute: WorkDetailsRoute = mock()
    private val rxSchedulerTest = RxScheduler(
            Schedulers.trampoline(),
            Schedulers.trampoline(),
            Schedulers.trampoline()
    )

    private val presenter = CastDetailsPresenter(
            view,
            getCastDetailsUseCase,
            workDetailsRoute,
            rxSchedulerTest
    )

    @Test
    fun `should load the cast details`() {
        val result = Pair(CAST_DETAILED_DOMAIN_MODEL, null)

        whenever(getCastDetailsUseCase(CAST_VIEW_MODEL.id)).thenReturn(Single.just(result))

        presenter.loadCastDetails(CAST_VIEW_MODEL)

        inOrder(view) {
            verify(view).onShowProgress()
            verify(view).onCastLoaded(CAST_DETAILED_VIEW_MODEL, null, null)
            verify(view).onHideProgress()
            verifyNoMoreInteractions()
        }
    }

    @Test
    fun `should load nothing if an error happens`() {
        whenever(getCastDetailsUseCase(CAST_VIEW_MODEL.id)).thenReturn(Single.error(Throwable()))

        presenter.loadCastDetails(CAST_VIEW_MODEL)

        inOrder(view) {
            verify(view).onShowProgress()
            verify(view).onErrorCastDetailsLoaded()
            verify(view).onHideProgress()
            verifyNoMoreInteractions()
        }
    }

    @Test
    fun `should open work details when a work is clicked`() {
        val route = mock<Route>()
        val itemViewHolder = mock<Presenter.ViewHolder>()

        whenever(workDetailsRoute.buildWorkDetailRoute(MOVIE_VIEW_MODEL)).thenReturn(route)

        presenter.workClicked(itemViewHolder, MOVIE_VIEW_MODEL)

        verify(view, only()).openWorkDetails(itemViewHolder, route)
    }
}
