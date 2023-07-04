package io.drdroid.assignment2.data.repo

import io.drdroid.assignment2.models.data.EpisodeModel
import io.drdroid.assignment2.models.data.SearchItemModel
import io.drdroid.assignment2.models.data.SeasonModel
import io.drdroid.assignment2.models.data.ShowModel
import retrofit2.http.Path
import retrofit2.http.Query

interface Repository {

    suspend fun search(
        @Query("q") q: String = ""
    ): ArrayList<SearchItemModel>

    suspend fun getSeasons(
        @Path("id") id: Int
    ): ArrayList<SeasonModel>

    suspend fun getSeasonEpisodes(
        @Path("id") id: Int
    ): ArrayList<EpisodeModel>

    suspend fun getShows(
        @Query("page") page: Int = 1
    ): ArrayList<ShowModel>
}