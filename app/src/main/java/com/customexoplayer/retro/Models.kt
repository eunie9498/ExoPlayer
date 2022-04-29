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