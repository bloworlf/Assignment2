package io.drdroid.assignment2.models.data


import com.google.gson.annotations.SerializedName

data class ShowModel(
    @SerializedName("averageRuntime")
    val averageRuntime: Int?,
    @SerializedName("dvdCountry")
    val dvdCountry: CountryModel?,
    @SerializedName("ended")
    val ended: String?,
    @SerializedName("externals")
    val externals: ExternalsModel?,
    @SerializedName("genres")
    val genres: List<String>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: ImageModel?,
    @SerializedName("language")
    val language: String?,
    @SerializedName("_links")
    val links: LinksModel?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("network")
    val network: NetworkModel?,
    @SerializedName("officialSite")
    val officialSite: String?,
    @SerializedName("premiered")
    val premiered: String?,
    @SerializedName("rating")
    val rating: RatingModel?,
    @SerializedName("runtime")
    val runtime: Int?,
    @SerializedName("schedule")
    val schedule: ScheduleModel?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("summary")
    val summary: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("updated")
    val updated: Int?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("webChannel")
    val webChannel: WebChannelModel?,
    @SerializedName("weight")
    val weight: Int?
)