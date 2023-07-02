package io.drdroid.assignment2.models.data


import com.google.gson.annotations.SerializedName

data class RatingModel(
    @SerializedName("average")
    val average: Double?
)