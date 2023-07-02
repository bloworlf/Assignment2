package io.drdroid.assignment2.models.data


import com.google.gson.annotations.SerializedName

data class ExternalsModel(
    @SerializedName("imdb")
    val imdb: String?,
    @SerializedName("thetvdb")
    val thetvdb: Int?,
    @SerializedName("tvrage")
    val tvrage: Int?
)