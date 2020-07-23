package br.com.brunamarcal.tmdbproject.data.network

import br.com.brunamarcal.tmdbproject.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {

    private fun initRetrofit(): Retrofit{
        val gson = GsonBuilder().setLenient().create() //Alguns Jsons podem dar erro no parse
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY) //Intercepta as chamadas de Json mostrando no log

        val client =  OkHttpClient.Builder().addInterceptor(logging).build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    val service: Service = initRetrofit().create(Service::class.java)
}