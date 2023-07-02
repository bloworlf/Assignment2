package io.drdroid.assignment2.utils

object Constants {

    const val BASE_URL: String = "https://api.tvmaze.com/"

    private const val SEARCH_SHOW: String = "search/shows"
    private const val SHOW_SCHEDULE: String = "schedule"
    private const val SHOW_SEASONS: String = "shows/{id}/seasons"
    private const val SHOW_EPISODES: String = "seasons/{id}/episodes"

//    private const val API_JSON_REQ: String = ".json"

    //    const val SEARCH: String = "$BASE_URL$SEARCH_SHOW$API_JSON_REQ"
    const val SEARCH: String = "$BASE_URL$SEARCH_SHOW"
    const val SCHEDULE: String = "$BASE_URL$SHOW_SCHEDULE"
    const val SEASONS: String = "$BASE_URL$SHOW_SEASONS"
    const val EPISODES: String = "$BASE_URL$SHOW_EPISODES"
}