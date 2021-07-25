package com.laube.tech.comiccollection.network

import com.laube.tech.comiccollection.models.response.MarvelResponse
import io.reactivex.rxjava3.core.Single

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelAPI {
    @GET("/v1/public/comics/{comicId}")
    fun getSingleComic(
        @Path("comicId") comicId: String,
        @Query("apikey", encoded = true) apiKey: String,
        @Query("ts", encoded = true) currentTimestamp: String,
        @Query("hash", encoded = true) hash: String
    ) : Single<MarvelResponse>


    @GET("/v1/public/series/{seriesId}/comics")
    fun getSeriesList(
        @Path("seriesId") seriesId: String,
        @Query("apikey", encoded = true) apiKey: String,
        @Query("ts", encoded = true) currentTimestamp: String,
        @Query("hash", encoded = true) hash: String,
        @Query("noVariants", encoded = true) noVariants: Boolean,
        @Query("orderBy", encoded = true) orderBy: String,
        @Query("limit", encoded = true) limit: Int,
        @Query("offset", encoded = true) offset: Int
    ) : Single<MarvelResponse>
}