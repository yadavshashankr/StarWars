package com.example.maytheforcebewith_shashankyadav.network

import okhttp3.logging.HttpLoggingInterceptor

class Interceptors {

    companion object{
         var httpLogging: HttpLoggingInterceptor? = null

           fun getLoggingInterceptor(): HttpLoggingInterceptor{

               if (httpLogging == null){
                   httpLogging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
               }

               return httpLogging as HttpLoggingInterceptor
           }

    }

}