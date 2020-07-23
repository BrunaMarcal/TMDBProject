package br.com.brunamarcal.tmdbproject.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class MovieResponse(

    val results: List<MovieResult>
)