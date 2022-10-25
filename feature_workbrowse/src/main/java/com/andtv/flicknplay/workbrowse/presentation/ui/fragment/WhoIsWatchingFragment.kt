package com.andtv.flicknplay.workbrowse.presentation.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.leanback.app.BrowseSupportFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andtv.flicknplay.presentation.extension.replaceFragment
import com.andtv.flicknplay.workbrowse.R
import com.andtv.flicknplay.workbrowse.presentation.model.WhoIsWatchingUserViewModel
import com.andtv.flicknplay.workbrowse.presentation.presenter.WhoIsWatchingPresenter
import com.andtv.flicknplay.workbrowse.presentation.ui.activity.MainActivity
import com.andtv.flicknplay.workbrowse.presentation.ui.adapter.ProfilesAdapter
import javax.inject.Inject

class WhoIsWatchingFragment : Fragment(), WhoIsWatchingPresenter.View, ProfilesAdapter.OnItemClicked,
    BrowseSupportFragment.MainFragmentAdapterProvider{

    private lateinit var rvProfile: RecyclerView
    private lateinit var adapter: ProfilesAdapter
    private var data: ArrayList<WhoIsWatchingUserViewModel> = arrayListOf()

    @Inject
    lateinit var presenter:WhoIsWatchingPresenter

    private val fragmentAdapter by lazy { BrowseSupportFragment.MainFragmentAdapter(this) }


    override fun onAttach(context: Context) {
        (requireActivity() as  MainActivity).mainActivityComponent
            .whoIsWatchingFragmentComponent()
            .create(this)
            .inject(this)
        super.onAttach(context)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.bindTo(this.lifecycle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_who_is_watching, container, false)
        rvProfile = view.findViewById(R.id.rv_profiles)
        Toast.makeText(requireActivity(),"Who is watching" , Toast.LENGTH_SHORT).show()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ProfilesAdapter(data,this)
        val layoutManager = GridLayoutManager(activity, 5)
        rvProfile.layoutManager = layoutManager
        rvProfile.adapter = adapter

        getWhoIsWatchingList()

    }

    companion object {

        @JvmStatic
        fun newInstance() = WhoIsWatchingFragment()
    }

    override fun onAddProfileClicked() {
        goToAvatarFragment()
    }

    private fun goToAvatarFragment() {
        (activity as MainActivity).replaceFragment(SelectAvatarFragment.newInstance())
    }

    private fun getWhoIsWatchingList(){
        presenter.getAllAccountUser(1)
    }

    private fun goToProfileLoginFragment(whoIsWatchingUserViewModel: WhoIsWatchingUserViewModel){
        (activity as MainActivity).replaceFragment(ProfileLoginFragment.newInstance(whoIsWatchingUserViewModel))
    }
    override fun onUserProfileClicked(whoIsWatchingUserViewModel: WhoIsWatchingUserViewModel) {
      goToProfileLoginFragment(whoIsWatchingUserViewModel)
    }

    override fun onProfilesLoaded(profiles: List<WhoIsWatchingUserViewModel>) {
        data.clear()
        data.addAll(profiles)
        adapter.notifyDataSetChanged()
    }

    override fun getMainFragmentAdapter() = fragmentAdapter

}