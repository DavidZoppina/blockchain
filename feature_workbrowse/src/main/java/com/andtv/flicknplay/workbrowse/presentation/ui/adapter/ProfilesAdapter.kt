package com.andtv.flicknplay.workbrowse.presentation.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.andtv.flicknplay.workbrowse.R
import com.andtv.flicknplay.workbrowse.presentation.model.WhoIsWatchingUserViewModel


class ProfilesAdapter(private val data: List<WhoIsWatchingUserViewModel>, private val onItemClicked: OnItemClicked):
    RecyclerView.Adapter<ProfilesAdapter.ProfilesViewHolder>() {
    private lateinit var context: Context
    inner class ProfilesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var avatar: ImageView
        lateinit var profileName: TextView

        init {
            avatar = itemView.findViewById(R.id.img_avatar)
            profileName = itemView.findViewById(R.id.tv_title)

            itemView.setOnFocusChangeListener{view, isFocused->
                if (isFocused) {
                    // run scale animation and make it bigger
                    val anim: Animation = AnimationUtils.loadAnimation(context, R.anim.scale_in_tv)
                    view.startAnimation(anim)
                    anim.fillAfter = true
                    avatar.background = context.getDrawable(R.drawable.avatar_focused)
                } else {
                    // run scale animation and make it smaller
                    val anim: Animation = AnimationUtils.loadAnimation(context, R.anim.scale_out_tv)
                    view.startAnimation(anim)
                    anim.fillAfter = true
                    avatar.background = context.getDrawable(R.drawable.avatar_unfocused)
                }
            }
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfilesViewHolder {
        context = parent.context
        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_item_rv_avatar, parent, false)
        v.isFocusable = true
        v.isFocusableInTouchMode = true
        return ProfilesViewHolder(v)
    }

    override fun onBindViewHolder(holder: ProfilesViewHolder, position: Int) {
        if(holder.absoluteAdapterPosition == data.size - 1){
            holder.profileName.text = data[holder.absoluteAdapterPosition].name
            holder.avatar.setImageResource(R.drawable.add_profile)
            holder.avatar.setBackgroundResource(R.drawable.bg_cirle)
            holder.itemView.setOnClickListener {
                onItemClicked.onAddProfileClicked()
            }
        }else{
            holder.profileName.text = data[holder.absoluteAdapterPosition].name
            holder.avatar.setImageResource(
                context.resources?.getIdentifier(
                "profile_${data[holder.absoluteAdapterPosition].avatar}",
                "drawable", context.packageName
            ) as Int)
            holder.itemView.setOnClickListener{
                onItemClicked.onUserProfileClicked(data[holder.absoluteAdapterPosition])
            }
        }
    }

    override fun getItemCount(): Int {
      return data.size
    }

    interface OnItemClicked{
        fun onAddProfileClicked()
        fun onUserProfileClicked(whoIsWatchingUserViewModel: WhoIsWatchingUserViewModel)
    }
}