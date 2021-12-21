package com.example.maytheforcebewith_shashankyadav.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroInstance {

    companion object{


        @Synchronized fun getRetroInstance(url: String): Retrofit {
            // receive retrofit instance
            return Retrofit.Builder().baseUrl(url).
            addConverterFactory(GsonConverterFactory.create()).client(OkHttpClients.getOkHttpClient1()).
            build()


        }
    }

}