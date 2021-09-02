package gizahost.alkora.pojo.league


import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("code")
    val code: Any,
    @SerializedName("flag")
    val flag: Any,
    @SerializedName("name")
    val name: String
)