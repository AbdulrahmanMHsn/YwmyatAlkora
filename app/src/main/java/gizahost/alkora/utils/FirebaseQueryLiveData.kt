//package gizahost.alkora.utils
//
//import androidx.lifecycle.LiveData
//import retrofit2.http.Query
//
//class FirebaseQueryLiveData : LiveData<DataSnapshot> {
//
//    private val LOG_TAG = "FirebaseQueryLiveData"
//
//    private var query: Query? = null
//    private val listener = MyValueEventListener()
//
//    fun FirebaseQueryLiveData(query: Query?) {
//        this.query = query
//    }
//
//    fun FirebaseQueryLiveData(ref: DatabaseReference?) {
//        query = ref
//    }
//
//    override fun onActive() {
//        Log.d(LOG_TAG, "onActive")
//        query.addValueEventListener(listener)
//    }
//
//    override fun onInactive() {
//        Log.d(LOG_TAG, "onInactive")
//        query.removeEventListener(listener)
//    }
//
//    private class MyValueEventListener : ValueEventListener {
//        fun onDataChange(dataSnapshot: DataSnapshot?) {
//            setValue(dataSnapshot)
//        }
//
//        fun onCancelled(databaseError: DatabaseError) {
//            Log.e(LOG_TAG, "Can't listen to query $query", databaseError.toException())
//        }
//    }
//}