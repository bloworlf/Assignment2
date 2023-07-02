package io.drdroid.assignment2.models.data


import com.google.gson.annotations.SerializedName

data class SeasonModel(
    @SerializedName("endDate")
    val endDate: String?,
    @SerializedName("episodeOrder")
    val episodeOrder: Int?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: ImageModel?,
    @SerializedName("_links")
    val links: LinksModel?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("network")
    val network: NetworkModel?,
    @SerializedName("number")
    val number: Int?,
    @SerializedName("premiereDate")
    val premiereDate: String?,
    @SerializedName("summary")
    val summary: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("webChannel")
    val webChannel: Any?
)