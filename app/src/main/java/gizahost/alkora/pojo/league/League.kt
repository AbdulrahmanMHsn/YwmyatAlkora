package gizahost.alkora.pojo.league


import android.graphics.drawable.Drawable
import android.media.Image
import androidx.core.graphics.drawable.DrawableCompat
import com.google.gson.annotations.SerializedName

class League(
    @SerializedName("id")
    val id: Int,
//    @SerializedName("logo")
//    val logo: String,
    @SerializedName("logo")
    val logo: Any,
    @SerializedName("name")
    val name: String
//    @SerializedName("type")
//    val type: String
)