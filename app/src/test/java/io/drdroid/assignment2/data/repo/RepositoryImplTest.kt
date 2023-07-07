package io.drdroid.assignment2.data.repo

import io.drdroid.assignment2.data.ListShows
import io.drdroid.assignment2.models.data.ShowModel
import io.drdroid.assignment2.network.MovieCall
import io.drdroid.assignment2.network.TvShowCall
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class RepositoryImplTest {

    lateinit var repo: Repository

    @Mock
    lateinit var tvShowCall: TvShowCall

    @Mock
    lateinit var movieCall: MovieCall

//    private val dispatcher:

    @Before
    fun onStart() {
        MockitoAnnotations.openMocks(this)

        repo = RepositoryImpl(
            tvShowCall = tvShowCall,
            movieCall = movieCall
        )
    }

    @After
    fun onFinish() {
        Mockito.clearAllCaches()
    }

    @Test
    fun testSearch() {
        runBlocking {
            Mockito.`when`(tvShowCall.search("girls")).thenReturn(
                ListShows.searchShow
            )
            assertEquals(ListShows.searchShow, repo.search(q = "girls"))
        }
    }

    @Test
    fun testSearch_Empty() {
        runBlocking {
            Mockito.`when`(tvShowCall.search("girls")).thenReturn(
                arrayListOf()
            )
            assertEquals(0, repo.search(q = "girls").size)
        }
    }

    @Test
    fun testSearch_Null() {
        runBlocking {
            Mockito.`when`(tvShowCall.search("girls")).thenReturn(
                null
            )
            assertNull(repo.search(q = "girls"))
        }
    }

    @Test
    fun getSeasons() {
    }

    @Test
    fun getSeasonEpisodes() {
    }

    @Test
    fun getShows() {
//        runBlocking {
////            repo.getShows(1)
//            Mockito.`when`(repo.getShows()).
//            assertEquals(0, repo.getShows().size)
//        }
    }
}