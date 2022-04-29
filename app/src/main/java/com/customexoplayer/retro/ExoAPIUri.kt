package com.customexoplayer.retro

import retrofit2.Call
import retrofit2.http.GET

interface ExoAPIUri {
    @GET("d6ca2108-4af6-41e9-9d5a-2408f05488ea")
    fun getMusics(): Call<MusicResponse>
}