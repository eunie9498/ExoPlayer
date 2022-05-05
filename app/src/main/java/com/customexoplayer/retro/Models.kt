package com.customexoplayer.retro

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MusicResponse(
    val musics: List<MusicData>? = arrayListOf()
) : Parcelable

@Parcelize
data class MusicData(
    val trackName: String,
    val playUrl: String,
    val artist: String,
    val coverUrl: String
) : Parcelable

@Parcelize
data class MusicList(
    val id: Long,
    val trackName:String,
    val streamUrl :String,
    val artist: String,
    val coverUrl: String,
    val isPlaying: Boolean?=false
): Parcelable