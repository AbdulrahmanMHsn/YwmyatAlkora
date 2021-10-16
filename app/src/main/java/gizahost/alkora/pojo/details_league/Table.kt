package gizahost.alkora.pojo.details_league


import com.google.gson.annotations.SerializedName

data class Table(
    @SerializedName("table")
    val table: List<Details>
)