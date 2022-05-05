package com.customexoplayer

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.customexoplayer.retro.MusicData
import com.customexoplayer.retro.MusicList

class ExoAdapter : ListAdapter<MusicList, ExoAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: MusicList) {
            val tvTrack = view.findViewById<TextView>(R.id.tvTrack)
            val tvArtist = view.findViewById<TextView>(R.id.tvArtist)
            val imgCover = view.findViewById<ImageView>(R.id.itemCover)
            tvTrack.text = item.trackName
            tvArtist.text = item.artist
            Glide.with(imgCover).load(item.coverUrl).into(imgCover)

            if (item.isPlaying == true) {
                itemView.setBackgroundColor(Color.GRAY)
            } else {
                itemView.setBackgroundColor(Color.TRANSPARENT)
            }
        }

    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<MusicList>() {
            override fun areContentsTheSame(oldItem: MusicList, newItem: MusicList): Boolean {
                return oldItem.trackName == newItem.trackName
            }

            override fun areItemsTheSame(oldItem: MusicList, newItem: MusicList): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.exo_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        currentList[position].also { model ->
            holder.bind(model)
        }
    }
}