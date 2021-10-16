package com.example.myapplication.network

import gizahost.alkora.network.api.NewsService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {

    val  BASE_URL_NEWS:String = "https://newsapi.org/"
    val  BASE_URL_FOOTBALL:String = "https://v3.football.api-sports.io/"
    val  BASE_URL_FOOTBALL_LEAGUE:String = "https://www.thesportsdb.com/"

    private fun getInstance(url:String): Retrofit {

        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getApiService(url:String): NewsService {
        return getInstance(url).create(NewsService::class.java)
    }

}