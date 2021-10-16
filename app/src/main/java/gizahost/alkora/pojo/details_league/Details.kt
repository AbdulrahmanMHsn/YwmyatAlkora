package gizahost.alkora.pojo.details_league


import com.google.gson.annotations.SerializedName

data class Details(
    @SerializedName("idStanding")
    val idStanding: String,
    @SerializedName("idLeague")
    val idLeague: String,
    @SerializedName("intDraw")
    val intDraw: String,
    @SerializedName("intGoalsAgainst")
    val intGoalsAgainst: String,
    @SerializedName("intGoalsFor")
    val intGoalsFor: String,
    @SerializedName("intLoss")
    val intLoss: String,
    @SerializedName("intPlayed")
    val intPlayed: String,
    @SerializedName("intPoints")
    val intPoints: String,
    @SerializedName("intRank")
    val intRank: String,
    @SerializedName("intWin")
    val intWin: String,
    @SerializedName("strTeam")
    val strTeam: String,
    @SerializedName("strTeamBadge")
    val strTeamBadge: String
)