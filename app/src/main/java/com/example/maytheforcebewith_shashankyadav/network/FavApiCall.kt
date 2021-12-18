package com.example.maytheforcebewith_shashankyadav.network

import com.example.maytheforcebewith_shashankyadav.globals.ApplicationConstant
import com.example.maytheforcebewith_shashankyadav.responses.FavData1
import com.example.maytheforcebewith_shashankyadav.responses.FavResponse1
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface FavApiCall {

    @POST("2ac34a4c-e982-4d69-907b-41f82748c269")
    @Headers("Content-Type: application/json")
    fun store(@Body data: FavData1): Call<FavResponse1>

    companion object Client {
    // creating api call for Favorites webhook
        var retroClient: FavApiCall? = null
        fun create(): FavApiCall {
            if (retroClient == null){

                val retrofit = RetroInstance.getRetroInstance(ApplicationConstant.APP_URL_FAV)
                retroClient = retrofit.create(FavApiCall::class.java)
            }
            return retroClient as FavApiCall
        }
    }
}