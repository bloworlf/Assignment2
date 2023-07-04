package io.drdroid.assignment2.data.repo

import io.drdroid.assignment2.network.MovieCall
import io.drdroid.assignment2.network.TvShowCall
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    val tvShowCall: TvShowCall,
    val movieCall: MovieCall
) : Repository {

    override suspend fun search(q: String) = tvShowCall.search(q)

    override suspend fun getSeasons(id: Int) = tvShowCall.getSeasons(id)

    override suspend fun getSeasonEpisodes(id: Int) = tvShowCall.getSeasonEpisodes(id)

    override suspend fun getShows(page: Int) = tvShowCall.getShows(page)
}