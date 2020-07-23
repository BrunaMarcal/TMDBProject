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

}
//discover/movie?api_key=079a8da33b1d25243977c4834788bcd1&language=pt-BR&include_adult=false