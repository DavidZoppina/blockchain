package com.andtv.flicknplay.workbrowse.presentation.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.andtv.flicknplay.data.Constants
import com.andtv.flicknplay.presentation.extension.popBackStack
import com.andtv.flicknplay.presentation.extension.replaceFragment
import com.andtv.flicknplay.workbrowse.R
import com.andtv.flicknplay.workbrowse.data.remote.model.ErrorResponse
import com.andtv.flicknplay.workbrowse.presentation.model.WhoIsWatchingUserViewModel

import com.andtv.flicknplay.workbrowse.presentation.presenter.ProfileLoginPresenter
import com.andtv.flicknplay.workbrowse.presentation.ui.activity.MainActivity
import javax.inject.Inject

private const val USER_TAG = "USER"


class ProfileLoginFragment: Fragment(),ProfileLoginPresenter.View, View.OnClickListener {

    private lateinit var editProfilePass: EditText
    private lateinit var btnOK :Button
    private lateinit var tvProfileName: TextView
    private lateinit var ivAvatar: ImageView
    private lateinit var loadingProgressBar: ProgressBar

    @Inject
    lateinit var presenter: ProfileLoginPresenter


    private val whoIsWatchingUserViewModel by lazy { arguments?.getSerializable(USER_TAG) as WhoIsWatchingUserViewModel }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     presenter.bindTo(this.lifecycle)
    }

    override fun onAttach(context: Context) {
        (requireActivity() as MainActivity).mainActivityComponent
            .profileLoginFragmentComponent()
            .create(this)
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile_login, container, false)
        editProfilePass = view.findViewById(R.id.edtxt_profile_pass)
        btnOK = view.findViewById(R.id.btn_ok)
        tvProfileName = view.findViewById(R.id.tv_title)
        ivAvatar = view.findViewById(R.id.img_avatar)
        loadingProgressBar = view.findViewById(R.id.loading)
        setEvents()
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        whoIsWatchingUserViewModel.let {
            tvProfileName.text = it.name
            ivAvatar.setImageResource(context?.resources?.getIdentifier(
                "profile_${it.avatar}",
                "drawable",context?.packageName) as Int)
        }
//        tvProfileName.text = requireArguments().getString("current-user")

        super.onViewCreated(view, savedInstanceState)

    }
   private fun setEvents(){

       btnOK.setOnClickListener(this)
   }
    companion object {

        @JvmStatic
        fun newInstance(whoIsWatchingUserViewModel: WhoIsWatchingUserViewModel) =
            ProfileLoginFragment().apply {
                arguments = bundleOf(
                    USER_TAG to whoIsWatchingUserViewModel
                )
            }
    }

    override fun onClick(v: View?) {
        presenter.profileLogin("${whoIsWatchingUserViewModel.id?.let { it } ?: -1}", editProfilePass.text.toString())
    }

    private fun goToTaskFragment() {

        (activity as MainActivity).replaceFragment(WorkBrowseFragment.newInstance())
    }

    override fun onLogged() {

        Constants.WHO_IS_WATCHING_USER = whoIsWatchingUserViewModel
        Constants.USER_ID = whoIsWatchingUserViewModel.id

        requireActivity().popBackStack()
        goToTaskFragment()
    }

    override fun onShowProgress() {
        loadingProgressBar.visibility = View.VISIBLE
    }

    override fun onHideProgress() {
        loadingProgressBar.visibility = View.GONE
    }

    override fun onErrorWorksLoaded(errorResponse: ErrorResponse) {
    }


}