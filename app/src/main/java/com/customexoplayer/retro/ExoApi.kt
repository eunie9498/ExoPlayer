package com.customexoplayer.retro

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ExoApi {

    companion object {
        fun initRetro(): ExoAPIUri {
            return Retrofit.Builder().baseUrl("https://run.mocky.io/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ExoAPIUri::class.java)
        }
    }
}