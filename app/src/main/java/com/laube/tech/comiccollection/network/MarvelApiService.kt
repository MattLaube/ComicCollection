package com.laube.tech.comiccollection.network

import com.laube.tech.comiccollection.models.response.MarvelResponse
import com.laube.tech.comiccollection.util.HashUtils
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit

import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MarvelApiService(var publicKey: String, var privateKey: String) {

    private val BASE_URL = "https://gateway.marvel.com:443"

    private val api: MarvelAPI

    init {
        val rxAdapter = RxJava3CallAdapterFactory.create()
        api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(rxAdapter)
            .build()
            .create(MarvelAPI::class.java)
    }

    fun getSingleComic(comicId: String): Single<MarvelResponse> {
        val currentTimestamp = System.currentTimeMillis()
        return api.getSingleComic(
            comicId,
            publicKey,
            currentTimestamp.toString(),
            generateHash(currentTimestamp).toString()
        )
    }

    fun getSeriesList(seriesId: String): Single<MarvelResponse> {
        val currentTimestamp = System.currentTimeMillis()
        return api.getSeriesList(
            seriesId,
            publicKey,
            currentTimestamp.toString(),
            generateHash(currentTimestamp).toString()
        )
    }

    private fun generateHash(timeStamp: Long): String? {
        val hashBase = timeStamp.toString() + privateKey + publicKey
        return HashUtils.md5(hashBase)

    }
}