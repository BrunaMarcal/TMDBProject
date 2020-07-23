package br.com.brunamarcal.tmdbproject.data.repository

import br.com.brunamarcal.tmdbproject.data.network.ApiService

class Repository {
    suspend fun getMovies(apiKey: String, language: String, includeAdult: Boolean) =
        ApiService.service.getMovie(apiKey, language, includeAdult)
}