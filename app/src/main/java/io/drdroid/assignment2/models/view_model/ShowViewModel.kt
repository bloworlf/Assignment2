package io.drdroid.assignment2.models.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.drdroid.assignment2.data.DistinctUntilChangedMutableLiveData
import io.drdroid.assignment2.data.repo.Repository
import io.drdroid.assignment2.models.data.ShowModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowViewModel @Inject constructor(
    private val repo: Repository
) : ViewModel() {

    var page: Int = 1
    private lateinit var shows: ArrayList<ShowModel>
    val showLiveData by lazy {
//        MutableLiveData<ArrayList<ShowModel>>()
        DistinctUntilChangedMutableLiveData<ArrayList<ShowModel>>()
    }

//    var isLoaded: Boolean = false

    fun getShowData(page: Int = 1) {
        CoroutineScope(Dispatchers.Main).launch {
            val result = repo.getShows(page)
            showLiveData.postValue(result) //smart and only update value when resources are available
//            showLiveData.value = result //urgently replace the current value
            this@ShowViewModel.page = page
        }
    }

    fun getShowByTag(tag: String, page: Int = 1) {
        CoroutineScope(Dispatchers.Main).launch {
            val result = repo.getShows(page)
//            if (page == 1) {
//                if (!this@ShowViewModel::shows.isInitialized) {
//                    shows = arrayListOf()
//                } else {
//                    shows.clear()
//                }
//            }
//            shows.addAll(result.filter { show -> tag in show.genres })
//            showLiveData.postValue(shows)
            // TODO: Fix duplicated data in live data
            showLiveData.postValue(result.filter { show -> tag in show.genres } as ArrayList<ShowModel>)
//            showLiveData.postValue(result) //smart and only update value when resources are available
            println("VALUE OF LIVEADTA: ${showLiveData.value?.size}")
            this@ShowViewModel.page = page
        }
    }

//    fun <T> LiveData<T>.distinctUntilChanged(): LiveData<T> = MediatorLiveData<T>().also { mediator ->
//        mediator.addSource(this, object : Observer<T> {
//            private var isInitialized = false
//            private var previousValue: T? = null
//
//            override fun onChanged(value: T) {
//                val wasInitialized = isInitialized
//                if (!isInitialized) {
//                    isInitialized = true
//                }
//                if(!wasInitialized || value != previousValue) {
//                    previousValue = value
//                    mediator.postValue(value)
//                }
//            }
//        })
//    }

}