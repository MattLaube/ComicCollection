package com.laube.tech.comiccollection.comiclist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.laube.tech.comiccollection.BuildConfig
import com.laube.tech.comiccollection.models.response.MarvelResponse
import com.laube.tech.comiccollection.models.response.Series
import com.laube.tech.comiccollection.network.MarvelApiService

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers


class ComicListViewModel() : ViewModel() {
    private val marvelApi = MarvelApiService(BuildConfig.PUBLIC_KEY, BuildConfig.PRIVATE_KEY)
    private val disposable = CompositeDisposable()
    private val LOG_TAG = ComicListViewModel::class.java.name

    val loading = MutableLiveData<Boolean>()
    val comics = MutableLiveData<List<Series>>()

    fun loadSeries(seriesId: String) {
        loading.value = true
        disposable.add(
            marvelApi.getSeriesList(seriesId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MarvelResponse>() {
                    override fun onSuccess(value: MarvelResponse?) {
                        loading.value = false
                        if(!value?.data?.results.isNullOrEmpty()){
                            comics.value = value?.data?.results?.firstOrNull()?.comics?.items
                        }
                        Log.d(LOG_TAG, value?.toString())
                    }

                    override fun onError(e: Throwable) {
                        loading.value = false
                        Log.e(LOG_TAG, e.localizedMessage)
                    }
                } ))
    }
}