package br.com.brunamarcal.tmdbproject.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieResponse(

    @SerializedName("results")
    val movieResult: List<MovieResult>
): Parcelable