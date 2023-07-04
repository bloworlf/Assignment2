package io.drdroid.assignment2.models.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.drdroid.assignment2.data.repo.Repository
import io.drdroid.assignment2.models.data.SeasonModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeasonViewModel @Inject constructor(
    private val repo: Repository
) : ViewModel(){

    val seasons: MutableLiveData<ArrayList<SeasonModel>> by lazy {
        MutableLiveData<ArrayList<SeasonModel>>()
    }

    fun getShowSeasons(id: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            val result = repo.getSeasons(id)
            seasons.postValue(result)
        }
    }
}