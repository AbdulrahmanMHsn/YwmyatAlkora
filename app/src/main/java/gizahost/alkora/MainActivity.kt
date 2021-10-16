package gizahost.alkora

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import gizahost.alkora.utils.ContextWrapper.Companion.changeLang
import gizahost.alkora.utils.ContextWrapper.Companion.setLocale
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context) {
        val context: Context = changeLang(newBase, Locale("en"))
        super.attachBaseContext(context)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setLocale(this,"en")
    }
}