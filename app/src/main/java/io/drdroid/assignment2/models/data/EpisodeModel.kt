package io.drdroid.assignment2.models.data


import com.google.gson.annotations.SerializedName

data class EpisodeModel(
    @SerializedName("airdate")
    val airdate: String?,
    @SerializedName("airstamp")
    val airstamp: String?,
    @SerializedName("airtime")
    val airtime: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: ImageModel?,
    @SerializedName("_links")
    val links: LinksModel?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("number")
    val number: Int?,
    @SerializedName("rating")
    val rating: RatingModel?,
    @SerializedName("runtime")
    val runtime: Int?,
    @SerializedName("season")
    val season: Int?,
    @SerializedName("summary")
    val summary: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("url")
    val url: String?
)