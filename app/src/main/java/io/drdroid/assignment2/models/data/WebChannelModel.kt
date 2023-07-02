package io.drdroid.assignment2.models.data


import com.google.gson.annotations.SerializedName

data class WebChannelModel(
    @SerializedName("country")
    val country: CountryModel?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("officialSite")
    val officialSite: String?
)