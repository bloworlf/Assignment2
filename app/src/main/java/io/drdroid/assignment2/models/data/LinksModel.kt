package io.drdroid.assignment2.models.data


import com.google.gson.annotations.SerializedName

data class LinksModel(
    @SerializedName("previousepisode")
    val previousepisode: PreviousepisodeModel?,
    @SerializedName("self")
    val self: SelfModel?
)