package com.customexoplayer.retro

fun MusicData.mapper(id: Long): MusicList =
    MusicList(
        id = id,
        streamUrl = this.playUrl,
        artist = this.artist,
        trackName = this.trackName,
        coverUrl = this.coverUrl)


fun MusicResponse.mapper(): PlayModel =
    PlayModel(
        playMusicModel = this.musics!!.mapIndexed{ idx, entity ->
            entity.mapper(idx.toLong())
        }
    )