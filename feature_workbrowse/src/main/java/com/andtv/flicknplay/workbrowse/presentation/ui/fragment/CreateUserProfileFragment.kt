package com.andtv.flicknplay.workbrowse.presentation.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.leanback.app.BrowseSupportFragment
import com.andtv.flicknplay.presentation.extension.replaceFragment
import com.andtv.flicknplay.workbrowse.R
import com.andtv.flicknplay.workbrowse.data.remote.model.ErrorResponse
import com.andtv.flicknplay.workbrowse.presentation.model.WhoIsWatchingUserViewModel
import com.andtv.flicknplay.workbrowse.presentation.presenter.CreateUserProfilePresenter
import com.andtv.flicknplay.workbrowse.presentation.ui.activity.MainActivity
import javax.inject.Inject

private const val AVATAR = "Avatar"
private const val EDIT = "Edit"
private const val WHO_IS_WATCHING = "UserViewModel"

class CreateUserProfileFragment : Fragment(), CreateUserProfilePresenter.View,
    BrowseSupportFragment.MainFragmentAdapterProvider {


    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var rgRating: RadioGroup
    private lateinit var ivAvatar: ImageView
    private lateinit var swKid: Switch
    private lateinit var btnCreate: Button
    private lateinit var loadingProgressBar: ProgressBar

    @Inject
    lateinit var presenter: CreateUserProfilePresenter

    private val avatarId by lazy { arguments?.let { arguments?.getString(AVATAR) } }
    private val isEditing by lazy { arguments?.let { arguments?.getBoolean(EDIT) } }
    private val whoIsWatchingUserViewModel by lazy { arguments?.let { arguments?.get(
        WHO_IS_WATCHING) } }

    private val fragmentAdapter by lazy { BrowseSupportFragment.MainFragmentAdapter(this) }


    override fun onAttach(context: Context) {
        (requireActivity() as MainActivity).mainActivityComponent
            .createUserProfileFragmentComponent()
            .create(this)
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_new_user, container, false)
        ivAvatar = view.findViewById(R.id.img_avatar)
        etUsername = view.findViewById(R.id.etUsername)
        etPassword = view.findViewById(R.id.etNewPassword)
        etConfirmPassword = view.findViewById(R.id.etConfirmPassword)
        swKid = view.findViewById(R.id.kidProfile)
        rgRating = view.findViewById(R.id.contentRatingRadioGroup)
        btnCreate = view.findViewById(R.id.btn_login)
        loadingProgressBar = view.findViewById(R.id.loading)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isEditing?.let {
            if(it) {
                whoIsWatchingUserViewModel?.let {
                    val profile = it as WhoIsWatchingUserViewModel
                    ivAvatar.setImageResource(context?.resources?.getIdentifier(
                        "profile_${profile.avatar}",
                        "drawable",context?.packageName) as Int)
                    swKid.isChecked = profile.kids as Boolean
                    etUsername.setText(profile.name)
                    when (profile.rating){
                        "G" ->  rgRating.check(R.id.radio_g)
                        "PG" -> rgRating.check(R.id.radio_pg)
                        "R" -> rgRating.check(R.id.radio_r)
                        "PG-13" -> rgRating.check(R.id.radio_pg13)
                        "NC-17" -> rgRating.check(R.id.radio_nc17)

                    }
                }
                ivAvatar.focusable = View.FOCUSABLE
                ivAvatar.isFocusableInTouchMode = true
                ivAvatar.isFocusedByDefault = true

                ivAvatar.setOnFocusChangeListener{ view, isFocused->
                    if (isFocused) {
                        // run scale animation and make it bigger
                        val anim: android.view.animation.Animation = android.view.animation.AnimationUtils.loadAnimation(context, com.andtv.flicknplay.workbrowse.R.anim.scale_in_tv)
                        view.startAnimation(anim)
                        anim.fillAfter = true
                        ivAvatar.background = requireContext().getDrawable(com.andtv.flicknplay.workbrowse.R.drawable.avatar_focused)
                    } else {
                        // run scale animation and make it smaller
                        val anim: android.view.animation.Animation = android.view.animation.AnimationUtils.loadAnimation(context, com.andtv.flicknplay.workbrowse.R.anim.scale_out_tv)
                        view.startAnimation(anim)
                        anim.fillAfter = true

                        ivAvatar.background = requireContext().getDrawable(com.andtv.flicknplay.workbrowse.R.drawable.avatar_unfocused)
                    }
                }

            }else{
                avatarId?.let {
                    ivAvatar.setImageResource(context?.resources?.getIdentifier(
                        "profile_${it}",
                        "drawable",context?.packageName) as Int)
                }
            }
        }


        ivAvatar.setOnClickListener{
            (requireActivity() as MainActivity).replaceFragment(SelectAvatarFragment.newInstance(
                isEditing,
                whoIsWatchingUserViewModel as WhoIsWatchingUserViewModel))
        }

        btnCreate.setOnClickListener{
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()
            val kid = swKid.isChecked
            var rating = ""
            when (rgRating.checkedRadioButtonId){
                R.id.radio_g -> {rating = "G"}
                R.id.radio_pg -> {rating = "PG"}
                R.id.radio_r -> {rating = "R"}
                R.id.radio_pg13 -> {rating = "PG-13"}
                R.id.radio_nc17 -> {rating = "NC-17"}
            }

            if(username.isEmpty()) {
                Toast.makeText(requireContext(), "Username can't be empty.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(rating.isEmpty()){
                Toast.makeText(requireContext(), "Select Rating Group!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(password.isEmpty()) {
                Toast.makeText(requireContext(), "Enter Password!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(password != confirmPassword) {
                Toast.makeText(requireContext(), "Password don't match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            isEditing?.let {

             if(!it) {
                 presenter.createUserProfile(
                     avatarId?.let { it } ?: "-1",
                     kid,
                     username,
                     password,
                     rating
                 )
             }else{
                 whoIsWatchingUserViewModel?.let {
                     val profile = it as WhoIsWatchingUserViewModel
                     presenter.updateUserProfile("${profile.id}", avatarId?.let { it } ?: "-1",
                         kid,
                         username,
                         password,
                         rating)
                 }
             }
            }

        }
    }


    companion object {

        @JvmStatic
        fun newInstance(avatarId: String, isEditing:Boolean = false, whoIsWatchingUserViewModel: WhoIsWatchingUserViewModel? = null) =
            CreateUserProfileFragment().apply {
                arguments = bundleOf(
                    AVATAR to avatarId,
                    EDIT to isEditing,
                    WHO_IS_WATCHING to whoIsWatchingUserViewModel
                )
            }
    }


    private fun goToWhoIsWatchingFragment() {
        (activity as MainActivity).replaceFragment(WhoIsWatchingFragment.newInstance())
    }

    override fun onProfileCreated() {
        goToWhoIsWatchingFragment()
    }

    override fun onProfileCreationFailed() {
        Toast.makeText(requireContext(), "Profile not created, Something went wrong", Toast.LENGTH_SHORT).show()
    }

    override fun onShowProgress() {
        loadingProgressBar.visibility = View.VISIBLE
    }

    override fun onHideProgress() {
        loadingProgressBar.visibility = View.GONE
    }

    override fun onErrorWorksLoaded(errorResponse: ErrorResponse) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(
            appContext, errorResponse.errors.email[0], Toast.LENGTH_LONG
        ).show()
    }

    override fun getMainFragmentAdapter() = fragmentAdapter
}