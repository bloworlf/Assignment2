package io.drdroid.assignment2.models.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor() : ViewModel() {

    val genreData by lazy {
        MutableLiveData<List<String>>()
    }

    fun saveGenre(genres: List<String>) {
        genreData.postValue(genres)
    }
}