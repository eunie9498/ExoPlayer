package com.customexoplayer.retro

fun MusicData.mapper(id: Long): MusicList =
    MusicList(
        id = id,
        streamUrl = this.playUrl,
        artist = this.artist,
        trackName = this.trackName,
        coverUrl = this.coverUrl)
