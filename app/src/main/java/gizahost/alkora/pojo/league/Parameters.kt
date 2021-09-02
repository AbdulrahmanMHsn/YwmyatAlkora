package gizahost.alkora.pojo.league


import com.google.gson.annotations.SerializedName

data class Parameters(
    @SerializedName("current")
    val current: String,
    @SerializedName("season")
    val season: String
)