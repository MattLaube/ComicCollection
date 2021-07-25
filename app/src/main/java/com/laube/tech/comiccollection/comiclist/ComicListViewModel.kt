package com.laube.tech.comiccollection.comiclist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.laube.tech.comiccollection.BuildConfig
import com.laube.tech.comiccollection.models.response.MarvelResponse
import com.laube.tech.comiccollection.models.response.Result
import com.laube.tech.comiccollection.models.response.Series
import com.laube.tech.comiccollection.network.MarvelApiService

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers


class ComicListViewModel() : ViewModel() {
    companion object {
        private const val ROW_LIMIT = 75
        private const val ORDER_BY = "issueNumber"
    }

    private val marvelApi = MarvelApiService(BuildConfig.PUBLIC_KEY, BuildConfig.PRIVATE_KEY)
    private val disposable = CompositeDisposable()
    private val LOG_TAG = ComicListViewModel::class.java.name
    private val offset = 0

    val loading = MutableLiveData<Boolean>()
    val comics = MutableLiveData<List<Result>>()

    fun loadSeries(seriesId: String) {
        loading.value = true
        disposable.add(
            marvelApi.getSeriesList(seriesId, ORDER_BY, ROW_LIMIT, offset)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MarvelResponse>() {
                    override fun onSuccess(value: MarvelResponse?) {
                        loading.value = false
                        if (!value?.data?.results.isNullOrEmpty()) {
                            comics.value = value?.data?.results
                        }
                    }

                    override fun onError(e: Throwable) {
                        loading.value = false
                        Log.e(LOG_TAG, e.localizedMessage)
                    }
                })
        )
    }
}