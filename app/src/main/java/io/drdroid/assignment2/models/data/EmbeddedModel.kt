package io.drdroid.assignment2.models.data


import com.google.gson.annotations.SerializedName

data class EmbeddedModel(
    @SerializedName("episodes")
    val episodes: List<EpisodeModel>?,
    @SerializedName("seasons")
    val seasons: List<SeasonModel>?
)