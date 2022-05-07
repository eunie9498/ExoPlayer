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
    val trackName: String,
    val streamUrl: String,
    val artist: String,
    val coverUrl: String,
    val isPlaying: Boolean? = false
) : Parcelable

@Parcelize
data class PlayModel(
    val playMusicModel: List<MusicList> = emptyList(),
    var currentPosition: Int = -1,
    var isInPlayListView: Boolean = true
) : Parcelable {
    fun getAdapterModels(): List<MusicList> {
        return playMusicModel.mapIndexed { idx, model ->
            val newItem = model.copy(
                isPlaying = idx == currentPosition
            )
            newItem
        }
    }

    fun updateCurrentPosition(model: MusicList) {
        currentPosition = playMusicModel.indexOf(model)
    }

    fun nextMusic(): MusicList? {
        if (playMusicModel.isEmpty()) return null

        //end detect
        currentPosition =
            if ((currentPosition + 1) == playMusicModel.size) 0 else currentPosition + 1

        return playMusicModel[currentPosition]
    }

    fun priMusic(): MusicList? {
        if (playMusicModel.isEmpty()) return null

        //first detect
        currentPosition =
            if ((currentPosition - 1) < 0) playMusicModel.lastIndex else currentPosition - 1

        return playMusicModel[currentPosition]
    }
}