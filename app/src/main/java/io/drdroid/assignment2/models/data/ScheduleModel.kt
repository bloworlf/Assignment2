package io.drdroid.assignment2.models.data


import com.google.gson.annotations.SerializedName

data class ScheduleModel(
    @SerializedName("days")
    val days: List<String?>?,
    @SerializedName("time")
    val time: String?
)