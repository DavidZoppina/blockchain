package com.andtv.flicknplay.workbrowse.presentation.ui.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.andtv.flicknplay.workbrowse.R
import java.lang.Exception
import java.lang.reflect.Field

class AvatarsAdapter( private val data: List<Int>, val onItemClicked: AvatarsAdapter.OnItemClicked):
RecyclerView.Adapter<AvatarsAdapter.AvatarViewHolder>() {
    private lateinit var context: Context
    inner class AvatarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvatarViewHolder {
        context = parent.context
        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_item_rv_avatar, parent, false)
        v.isFocusable = true
        v.isFocusableInTouchMode = true
        return AvatarViewHolder(v)
    }

    override fun onBindViewHolder(holder: AvatarViewHolder, position: Int) {

            holder.profileName.visibility =View.GONE

                 holder.avatar.setImageResource(data[holder.absoluteAdapterPosition])

            holder.itemView.setOnClickListener{
                onItemClicked.onAvatarSelection("${holder.absoluteAdapterPosition+1}")
            }

    }

    fun getResId(resName: String, c: Class<Drawable>): Int{
        try{
            val field: Field = c.getDeclaredField(resName)
            return field.getInt(field)
        }catch (ex: Exception){
            return -1
        }
    }

    override fun getItemCount(): Int {
        return  if(data == null) 0 else data.size
    }

    interface OnItemClicked{
        fun onAvatarSelection(avatarId: String)
    }
}
