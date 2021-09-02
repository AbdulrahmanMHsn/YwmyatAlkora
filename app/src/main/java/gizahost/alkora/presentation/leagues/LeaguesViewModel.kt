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

//    fun getNews(): MutableLiveData<News> {
//
//
//        GlobalScope.launch {
//            Dispatchers.IO
//
//            try {
//                val response = RetrofitClient.getApiService(RetrofitClient.BASE_URL_NEWS)
//                    .getNews().execute()
//
//                // Check if response was successful.
//                withContext(Dispatchers.Main) {
//
//                    if (response.isSuccessful) {
//
//                        mutableNews.value = response.body()
//
//                        Log.i(Companion.TAG, "getDataApis: Response is successful ${mutableNews.value!!.status}")
//                    } else {
//                        Log.i(Companion.TAG, "getDataApis: Response is not successful")
//                    }
//
//                }
//            } catch (e: Exception) {
//                Log.i(Companion.TAG, "getDataApis: " + e.message)
//            }
//        }
//
//        return mutableNews
//    }

    companion object {
        private const val TAG = "NewsViewModel"
    }

}