package com.shashank.maytheforcebewith_shashankyadav.network

import com.shashank.maytheforcebewith_shashankyadav.globals.ApplicationConstant
import com.shashank.maytheforcebewith_shashankyadav.responses.*
import com.shashank.maytheforcebewith_shashankyadav.responses.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface ApiCall {

    @GET("people")
    suspend fun getPeople(@Query("page") page: Int): Response<People1>

    @GET("planets")
    suspend fun getPlanets(@Query("page") page: Int): Response<Planets1>

    @GET("films")
    suspend fun getFilms(@Query("page") page: Int): Response<Films1>

    @GET("species")
    suspend fun getSpecies(@Query("page") page: Int): Response<Species1>

    @GET("vehicles")
    suspend fun getVehicles(@Query("page") page: Int): Response<Vehicles1>

    @GET("starships")
    suspend fun getStarships(@Query("page") page: Int): Response<Starships1>


    companion object Client {
        // creating api call for Star Wars api
        var retroClient: ApiCall? = null
        @Synchronized
        fun create(): ApiCall {
            if (retroClient == null) {
                val retrofit = RetroInstance.getRetroInstance(ApplicationConstant.APP_URL)
                retroClient = retrofit.create(ApiCall::class.java)
            }
            return retroClient as ApiCall
        }
    }
}