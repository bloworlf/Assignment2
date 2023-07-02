package io.drdroid.assignment2.models.data


import com.google.gson.annotations.SerializedName

data class CountryModel(
    @SerializedName("code")
    val code: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("timezone")
    val timezone: String?
)