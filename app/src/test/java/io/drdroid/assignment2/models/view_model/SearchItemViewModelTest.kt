package io.drdroid.assignment2.models.view_model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.drdroid.assignment2.data.ListShows
import io.drdroid.assignment2.data.repo.RepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SearchItemViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    //    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = StandardTestDispatcher()

    @Mock
    lateinit var repo: RepositoryImpl

    //    private lateinit var tvShowCall: TvShowCall
//    private lateinit var movieCall: MovieCall
    lateinit var viewModel: SearchItemViewModel

    //    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun onStart() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

//        repo = RepositoryImpl(
//            tvShowCall = tvShowCall,
//            movieCall = movieCall
//        )
        viewModel = SearchItemViewModel(repo)
    }

    @After
    fun onFinish() {
        Mockito.clearAllCaches()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun verifyGetSearchItemData() = runTest {
        Mockito.`when`(repo.search("girls")).thenReturn(ListShows.searchShow)

        viewModel.searchShow("girls")

        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(ListShows.searchShow, viewModel.searchItemData.value!!)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun verifyGetSearchItemData_Empty() = runTest {
        Mockito.`when`(repo.search("girls")).thenReturn(arrayListOf())

        viewModel.searchShow("girls")

        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(0, viewModel.searchItemData.value?.size)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun verifyGetSearchItemData_Null() = runTest {
        Mockito.`when`(repo.search("girls")).thenReturn(null)

        viewModel.searchShow("girls")

        testDispatcher.scheduler.advanceUntilIdle()

        assertNull(viewModel.searchItemData.value)
    }


    @Test
    fun setLoaded() {
    }

    @Test
    fun searchShow() {
    }
}