package io.drdroid.assignment2.models.data


import com.google.gson.annotations.SerializedName

data class SearchItemModel(
    @SerializedName("score")
    val score: Double,
    @SerializedName("show")
    val show: ShowModel
)