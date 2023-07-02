package io.drdroid.assignment2.models.data


import com.google.gson.annotations.SerializedName

data class ImageModel(
    @SerializedName("medium")
    val medium: String?,
    @SerializedName("original")
    val original: String?
)