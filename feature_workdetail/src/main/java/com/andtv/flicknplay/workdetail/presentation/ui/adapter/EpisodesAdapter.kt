package com.andtv.flicknplay.workdetail.presentation.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import com.andtv.flicknplay.workdetail.presentation.ui.adapter.EpisodesAdapter.EpisodesViewHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.andtv.flicknplay.presentation.extension.loadImageInto
import com.andtv.flicknplay.workdetail.R
import com.andtv.flicknplay.workdetail.presentation.model.VideoViewModel

class EpisodesAdapter (private val episodes: List<VideoViewModel>?, private val episodeSelected: EpisodeSelected?): RecyclerView.Adapter<EpisodesViewHolder>() {

    var context: Context? = null
    var selectionEpisode:Int  = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodesViewHolder {
        context = parent.context
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_episode, parent, false)
        v.isFocusable = true
        v.isFocusableInTouchMode = true
        return EpisodesViewHolder(v)
    }

    override fun onBindViewHolder(holder: EpisodesViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.title.text = episodes?.get(position)?.name

        if(episodes?.get(position)?.thumbnail!=null )
            holder.thumbnail.loadImageInto(episodes.get(position).thumbnail!!)


        holder.itemView.setOnClickListener{
                holder.itemView.requestFocus()
                episodeSelected?.onEpisodesSelected(it, position)
        }
        if(position == selectionEpisode)
           holder.itemView.requestFocus()
//        else
//            holder.itemView.clearFocus()
    }

    override fun getItemCount(): Int {
        return if(episodes == null) 0 else episodes.size
    }

    inner class EpisodesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView
        var thumbnail: ImageView
        init {
            title = itemView.findViewById(R.id.title)
            thumbnail = itemView.findViewById(R.id.ivThumbnail)


            itemView.setOnFocusChangeListener{view, isFocused->
                if (isFocused) {
                    // run scale animation and make it bigger
                    val anim: Animation = AnimationUtils.loadAnimation(context, R.anim.scale_in_tv)
                    view.startAnimation(anim)
                    anim.fillAfter = true
                } else {
                    // run scale animation and make it smaller
                    val anim: Animation = AnimationUtils.loadAnimation(context, R.anim.scale_out_tv)
                    view.startAnimation(anim)
                    anim.fillAfter = true
                }
            }

        }
    }


    interface EpisodeSelected {
        fun onEpisodesSelected(view: View?, position: Int?)
    }
}