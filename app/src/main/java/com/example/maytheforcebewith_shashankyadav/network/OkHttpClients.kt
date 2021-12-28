package com.example.maytheforcebewith_shashankyadav.network

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class OkHttpClients {

    companion object {
        var okHttpClient: OkHttpClient? = null


        @Synchronized
        fun getOkHttpClient1(): OkHttpClient {

            if (okHttpClient == null) {
                okHttpClient = OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(Interceptors.getLoggingInterceptor()).build()
            }
            return okHttpClient as OkHttpClient
        }
    }

}