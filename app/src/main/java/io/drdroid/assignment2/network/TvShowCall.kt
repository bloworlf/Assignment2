package io.drdroid.assignment2.network

import io.drdroid.assignment2.models.data.EpisodeModel
import io.drdroid.assignment2.models.data.SearchItemModel
import io.drdroid.assignment2.models.data.SeasonModel
import io.drdroid.assignment2.models.data.ShowModel
import io.drdroid.assignment2.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowCall {

    @GET(Constants.SEARCH)
    suspend fun search(
        @Query("q") q: String = ""
    ): ArrayList<SearchItemModel>

    @GET(Constants.SCHEDULE)
    suspend fun schedule(): ArrayList<SearchItemModel>

    @GET(Constants.SEASONS)
    suspend fun getSeasons(
        @Path("id") id: Int
    ): ArrayList<SeasonModel>

    @GET(Constants.EPISODES)
    suspend fun getSeasonEpisodes(
        @Path("id") id: Int
    ): ArrayList<EpisodeModel>

    @GET(Constants.SHOWS)
    suspend fun getShows(
        @Query("page") page: Int = 1
    ): ArrayList<ShowModel>
}