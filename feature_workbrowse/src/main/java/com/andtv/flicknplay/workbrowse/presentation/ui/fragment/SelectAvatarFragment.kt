package com.andtv.flicknplay.workbrowse.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andtv.flicknplay.presentation.extension.replaceFragment
import com.andtv.flicknplay.workbrowse.R
import com.andtv.flicknplay.workbrowse.presentation.model.WhoIsWatchingUserViewModel
import com.andtv.flicknplay.workbrowse.presentation.ui.adapter.AvatarsAdapter


private const val EDIT = "Edit"
private const val WHO_IS_WATCHING = "UserViewModel"

class SelectAvatarFragment : Fragment(), AvatarsAdapter.OnItemClicked {

    private lateinit var rvAvatar: RecyclerView
    private lateinit var adapter: AvatarsAdapter


    private val isEditing by lazy { arguments?.let { arguments?.getBoolean(EDIT) } }
    private val whoIsWatchingUserViewModel by lazy { arguments?.let { arguments?.get(
        WHO_IS_WATCHING) } }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_select_avatar, container, false)
        rvAvatar = view.findViewById(R.id.rv_avatars)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = arrayListOf<Int>()


        for(i in 1..141){
            list.add(activity?.resources?.getIdentifier("profile_$i","drawable",activity?.packageName) as Int)
        }

        adapter = AvatarsAdapter(list ,this)


        val layoutManager = GridLayoutManager(activity, 6)
        rvAvatar.layoutManager = layoutManager
        rvAvatar.adapter = adapter
    }

    companion object {

        @JvmStatic
        fun newInstance(isEditing:Boolean? = false,
                          whoIsWatchingUserViewModel: WhoIsWatchingUserViewModel? = null)
        = SelectAvatarFragment().apply {
            arguments = bundleOf(
                EDIT to isEditing,
                WHO_IS_WATCHING to whoIsWatchingUserViewModel
            )
        }
    }


    override fun onAvatarSelection(avatarId: String) {
        isEditing?.let{ edit->
            if(!edit)
                requireActivity().replaceFragment(CreateUserProfileFragment.newInstance(avatarId))
            else{
                whoIsWatchingUserViewModel.let {
                    (it as WhoIsWatchingUserViewModel).avatar = avatarId
                    requireActivity().replaceFragment(CreateUserProfileFragment.newInstance(avatarId, edit , it))
                }

            }
        }
    }


}