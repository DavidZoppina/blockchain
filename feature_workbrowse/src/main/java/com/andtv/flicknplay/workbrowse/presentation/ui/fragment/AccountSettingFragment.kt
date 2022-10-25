package com.andtv.flicknplay.workbrowse.presentation.ui.fragment

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.app.GuidedStepSupportFragment
import androidx.leanback.widget.GuidanceStylist
import androidx.leanback.widget.GuidedAction
import com.andtv.flicknplay.presentation.extension.replaceAndAddFragment
import com.andtv.flicknplay.presentation.extension.replaceFragment
import com.andtv.flicknplay.workbrowse.R
import com.andtv.flicknplay.workbrowse.presentation.presenter.AccountSettingPresenter
import com.andtv.flicknplay.workbrowse.presentation.ui.activity.MainActivity
import javax.inject.Inject

class AccountSettingFragment : GuidedStepSupportFragment(), AccountSettingPresenter.View,
    BrowseSupportFragment.MainFragmentAdapterProvider {

    private val fragmentAdapter by lazy { BrowseSupportFragment.MainFragmentAdapter(this) }


    @Inject
    lateinit var presenter: AccountSettingPresenter


    override fun onAttach(context: Context) {
        (requireActivity() as MainActivity).mainActivityComponent
            .accountSettingFragmentComponent()
            .create(this)
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreateGuidance(savedInstanceState: Bundle?): GuidanceStylist.Guidance {
        val title = getString(R.string.app_name)
        val breadcrumb = getString(R.string.about)
        val description = getString(R.string.about_description)
        val icon = requireActivity().getDrawable(R.drawable.app_icon)

        return GuidanceStylist.Guidance(title, description, breadcrumb, icon)
    }

    override fun onCreateActions(actions: MutableList<GuidedAction>, savedInstanceState: Bundle?) {
        presenter.getGuidedActions().forEach { (id, title) ->
            addAction(actions, id, title)
        }
        super.onCreateActions(actions, savedInstanceState)
    }

    override fun onGuidedActionClicked(action: GuidedAction?) {
        ( requireActivity() as MainActivity ).replaceAndAddFragment(AccountSettingDetailFragment(),"AccountSettingDetailFragment")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainFragmentAdapter.fragmentHost.notifyViewCreated(mainFragmentAdapter)

    }
    override fun openLink(link: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), R.string.failed_open_link, Toast.LENGTH_LONG).show()
        }
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


    companion object {
        fun newInstance() =
            AccountSettingFragment()
    }
}