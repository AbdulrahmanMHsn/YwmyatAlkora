package gizahost.alkora.pojo.league


import com.google.gson.annotations.SerializedName

data class Coverage(
    @SerializedName("fixtures")
    val fixtures: Fixtures,
    @SerializedName("odds")
    val odds: Boolean,
    @SerializedName("players")
    val players: Boolean,
    @SerializedName("predictions")
    val predictions: Boolean,
    @SerializedName("standings")
    val standings: Boolean,
    @SerializedName("top_assists")
    val topAssists: Boolean,
    @SerializedName("top_cards")
    val topCards: Boolean,
    @SerializedName("top_scorers")
    val topScorers: Boolean
)