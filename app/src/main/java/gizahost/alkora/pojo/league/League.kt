package gizahost.alkora.pojo.league


import com.google.gson.annotations.SerializedName

data class League(
    @SerializedName("id")
    val id: Int,
    @SerializedName("logo")
    val logo: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: String
)