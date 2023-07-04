package io.drdroid.assignment2.models.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.drdroid.assignment2.data.repo.Repository
import io.drdroid.assignment2.models.data.EpisodeModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeViewModel @Inject constructor(
    private val repo: Repository
) : ViewModel() {

    val episodes: MutableLiveData<ArrayList<EpisodeModel>> by lazy {
        MutableLiveData<ArrayList<EpisodeModel>>()
    }

    fun getEpisodesSeason(id: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            val result = repo.getSeasonEpisodes(id)
            episodes.postValue(result)
        }
    }

}
