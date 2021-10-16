package gizahost.alkora.presentation.leagues

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.network.RetrofitClient
import gizahost.alkora.pojo.News
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LeaguesViewModel: ViewModel() {


    var leagues  = liveData(Dispatchers.IO) {
        try {
            val response = RetrofitClient.getApiService(RetrofitClient.BASE_URL_FOOTBALL)
                .getAllLeagues().execute()

            // Check if response was successful.
            withContext(Dispatchers.Main) {

                if (response.isSuccessful) {

                    emit(response.body())

                }
            }
        } catch (e: Exception) {
            Log.i(Companion.TAG, "getDataApis: " + e.message)
        }
    }



    fun table(id:String)  = liveData(Dispatchers.IO) {
        try {
            val response = RetrofitClient.getApiService(RetrofitClient.BASE_URL_FOOTBALL_LEAGUE)
                .getTable(id).execute()

            // Check if response was successful.
            withContext(Dispatchers.Main) {

                if (response.isSuccessful) {

                    emit(response.body())

                }
            }
        } catch (e: Exception) {
            Log.i(Companion.TAG, "getDataApiss: " + e.message)
        }
    }


    companion object {
        private const val TAG = "NewsViewModel"
    }

}