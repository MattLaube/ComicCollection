package com.laube.tech.comiccollection.comicdetail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.laube.tech.comiccollection.BuildConfig

import com.laube.tech.comiccollection.models.response.MarvelResponse
import com.laube.tech.comiccollection.network.MarvelApiService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class ComicDetailViewModel : ViewModel(){
    private val marvelApi = MarvelApiService(BuildConfig.PUBLIC_KEY, BuildConfig.PRIVATE_KEY)
    private val disposable = CompositeDisposable()
    private val LOG_TAG = ComicDetailViewModel::class.java.name

    val loading = MutableLiveData<Boolean>()
    val comic = MutableLiveData<MarvelResponse>()

    fun loadIssue(issueId: String) {
        loading.value = true
        disposable.add(
            marvelApi.getSingleComic(issueId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MarvelResponse>() {
                    override fun onSuccess(response: MarvelResponse?) {
                        loading.value = false
                        response.let {
                            comic.value = it
                        }
                        Log.d(LOG_TAG, response?.toString().toString())
                    }

                    override fun onError(e: Throwable) {
                        loading.value = false
                        Log.e(LOG_TAG, e.localizedMessage)
                    }
                } ))
    }
}