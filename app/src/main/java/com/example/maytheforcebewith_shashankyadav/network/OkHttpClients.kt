package com.example.maytheforcebewith_shashankyadav.network

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class OkHttpClients {

    companion object{
        var okHttpClient: OkHttpClient? = null


        @Synchronized fun getOkHttpClient1(): OkHttpClient{

            if (okHttpClient == null){
                okHttpClient = OkHttpClient.Builder().connectTimeout(2, TimeUnit.MINUTES).writeTimeout(2, TimeUnit.MINUTES)
                    .readTimeout(2, TimeUnit.MINUTES).addInterceptor(Interceptors.getLoggingInterceptor()).build()
            }
            return okHttpClient as OkHttpClient
        }
    }

}