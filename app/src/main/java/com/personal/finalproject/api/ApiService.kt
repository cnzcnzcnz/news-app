package com.personal.finalproject.api

import android.content.Context
import com.personal.finalproject.BuildConfig
import com.personal.finalproject.models.Response
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import java.io.IOException
import java.util.concurrent.TimeUnit

interface ApiService {


    @GET("v2/top-headlines?country=us&category=business&apiKey=0938128d342349ec8e55fce6cb8f7346")
    fun getTopHeadlines(): Call<Response>

    object Network{
        fun build(): ApiService{
            val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
            client.addInterceptor(interceptor)
            client.connectTimeout(30, TimeUnit.SECONDS)
            client.readTimeout(30, TimeUnit.SECONDS)
            client.writeTimeout(30, TimeUnit.SECONDS)

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .callFactory(client.build())
                .baseUrl(BuildConfig.API)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}