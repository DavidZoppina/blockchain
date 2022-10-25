package com.andtv.flicknplay.workbrowse.presentation.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.app.GuidedStepSupportFragment
import androidx.leanback.widget.GuidedAction
import com.andtv.flicknplay.presentation.extension.replaceAndAddFragment
import com.andtv.flicknplay.workbrowse.presentation.presenter.AccountPresenter
import com.andtv.flicknplay.workbrowse.presentation.ui.activity.MainActivity
import javax.inject.Inject

class AccountFragment: GuidedStepSupportFragment(), AccountPresenter.View,
    BrowseSupportFragment.MainFragmentAdapterProvider {

    private val fragmentAdapter by lazy { BrowseSupportFragment.MainFragmentAdapter(this) }

    @Inject
    lateinit var presenter: AccountPresenter

    override fun onAttach(context: Context) {
        (requireActivity() as MainActivity).mainActivityComponent
            .accountFragmentComponent()
            .create(this)
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreateActions(actions: MutableList<GuidedAction>, savedInstanceState: Bundle?) {
        presenter.getGuidedActions().forEach { (id, title) ->
            addAction(actions, id, title)
        }

        super.onCreateActions(actions, savedInstanceState)
    }

    override fun onGuidedActionClicked(action: GuidedAction?) {
        action?.let {
            presenter.guidedActionClicked(action.id)
        }
    }

    override fun onAccountClicked() {
        requireActivity().replaceAndAddFragment(AccountSettingFragment.newInstance(), "AccountSettingFragment")
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainFragmentAdapter.fragmentHost.notifyViewCreated(mainFragmentAdapter)
    }

    override fun getMainFragmentAdapter() = fragmentAdapter


    private fun addAction(actions: MutableList<GuidedAction>, id: Long, @StringRes title: Int) {
        actions.add(
            GuidedAction.Builder(requireContext())
                .id(id)
                .title(title)
                .build()
        )
    }

    companion object{
        fun newInstance() = AccountFragment()
    }
}