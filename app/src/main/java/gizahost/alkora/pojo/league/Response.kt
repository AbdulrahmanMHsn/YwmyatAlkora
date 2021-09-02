package gizahost.alkora.pojo.league


import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("country")
    val country: Country,
    @SerializedName("league")
    val league: League,
    @SerializedName("seasons")
    val seasons: List<Season>
)