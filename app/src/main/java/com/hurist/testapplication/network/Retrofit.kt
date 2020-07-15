package com.hurist.wanandroid.data

import android.util.Log
import android.util.Log.DEBUG
import android.util.Log.VERBOSE
import com.hurist.testapplication.network.service.WanAndroid
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.Logger
import com.ihsanbal.logging.LoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {

    private val retrofit by lazy {

        val httpLogger = LoggingInterceptor.Builder()
            .setLevel(Level.BODY)
            .log(DEBUG)
            .build()

        val client = OkHttpClient.Builder().addInterceptor(httpLogger).build()

        Retrofit.Builder()
            .baseUrl("https://www.wanandroid.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    val wanAndroid = retrofit.create(WanAndroid::class.java)

}