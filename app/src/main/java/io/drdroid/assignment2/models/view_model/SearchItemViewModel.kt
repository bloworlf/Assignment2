package io.drdroid.assignment2.models.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.drdroid.assignment2.data.repo.Repository
import io.drdroid.assignment2.models.data.SearchItemModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchItemViewModel @Inject constructor(
    private val repo: Repository
) : ViewModel() {

    val searchItemData by lazy {
        MutableLiveData<ArrayList<SearchItemModel>>()
    }

    var isLoaded: Boolean = false

    fun searchShow(query: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val result = repo.search(q = query)
            searchItemData.postValue(result)

            isLoaded = true
        }
    }
}