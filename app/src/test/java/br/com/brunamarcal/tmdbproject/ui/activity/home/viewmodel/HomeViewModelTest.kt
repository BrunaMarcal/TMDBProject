package br.com.brunamarcal.tmdbproject.ui.activity.home.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.brunamarcal.tmdbproject.core.State
import br.com.brunamarcal.tmdbproject.data.model.MovieResponse
import br.com.brunamarcal.tmdbproject.data.model.MovieResult
import br.com.brunamarcal.tmdbproject.data.repository.Repository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.Mockito.doReturn

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val mockRepository = Mockito.mock(Repository::class.java)
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        testCoroutineDispatcher.cleanupTestCoroutines()
        Dispatchers.resetMain()
    }

    @Test
    fun `when Call getMovies if Success Sets MovieResponse`() = testCoroutineDispatcher.runBlockingTest{
        //preparação
        val viewModel = HomeViewModel(mockRepository, testCoroutineDispatcher)
        testCoroutineDispatcher.pauseDispatcher()

        doReturn(mockMovieResponse()).`when`(mockRepository).getMovies("1234", "pt-BR", true)

        //ação
        viewModel.getMovie("1234", "pt-BR", true)

        //assert - verificação
        testCoroutineDispatcher.resumeDispatcher()
        assertThat(viewModel.movieResponse.value).isEqualTo(State.success(mockMovieResponse()))

    }

     private fun mockMovieResponse() = MovieResponse (
            listOf(
                MovieResult(
                    10, "posterPath",
                    "overView", "10/09/2013",
                    listOf<Int>(1, 2, 3), "Lalaland", 10.9
                )
            )
         )
     }
















