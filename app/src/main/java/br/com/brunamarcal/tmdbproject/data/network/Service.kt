package br.com.brunamarcal.tmdbproject.data.network

import br.com.brunamarcal.tmdbproject.data.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {

    @GET("discover/movie")
    suspend fun getMovie(@Query("api_key") apiKey: String,
                 @Query("language") language: String,
                 @Query("include_adult") includeAdult: Boolean): Response<MovieResponse>

    @GET("discover/movie")
    suspend fun getMovieGenre(@Query("api_key") apiKey: String,
                              @Query("with_genres") withGenres: Int,
                              @Query("language") language: String): MovieResponse
}

//discover/movie?api_key=079a8da33b1d25243977c4834788bcd1&language=pt-BR&include_adult=false


//discover/movie?api_key=079a8da33b1d25243977c4834788bcd1&with_genres=28&language=en-US

//      UrlBase + path + ?api_key=APIKEY + &language=LANGUAGE + &include_adult=ADULT