package io.drdroid.assignment2.interfaces

interface SeasonListener {
    fun onSeasonSelected(id: Int, colors: Map<String, Int>)
}