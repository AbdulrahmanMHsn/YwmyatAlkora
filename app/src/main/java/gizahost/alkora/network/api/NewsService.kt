package gizahost.alkora.network.api

import gizahost.alkora.pojo.News
import gizahost.alkora.pojo.league.LeagueList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NewsService {

    @GET("v2/top-headlines")
    fun getNews(
        @Query("country") country: String = "eg",
        @Query("category") category: String= "sport",
        @Query("apiKey") apiKey: String = "ee267f17eeb74ac0965887a772ef6f90"
    ): Call<News>


    @GET("leagues")
    fun getAllLeagues(
        @Header("x-rapidapi-key") apiKey:String = "7533df7f4f6ef9b0ff2cc93221e7d80c",
        @Query("current") current:String = "true",
        @Query("season") season:Int = 2021
    ): Call<LeagueList>
}