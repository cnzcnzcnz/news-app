package com.personal.finalproject.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.personal.finalproject.NewsHomeRepository
import com.personal.finalproject.api.ApiService
import com.personal.finalproject.models.Articles
import com.personal.finalproject.models.LoadingStatus
import com.personal.finalproject.models.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.await

class NewsHomeViewModel() : ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _newsList = MutableLiveData<ArrayList<Articles>?>()
    val newsList: LiveData<ArrayList<Articles>?> get() = _newsList

    private val _loadingStatus = MutableLiveData<LoadingStatus>()
    val loadingStatus: LiveData<LoadingStatus> get() = _loadingStatus

    init{
        fetchData()
    }

    fun fetchData(){
        coroutineScope.launch {
            _loadingStatus.value = LoadingStatus.LOADING
            ApiService.Network.build().getTopHeadlines().enqueue(object: Callback<Response> {
                override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                    val body = response.body()
                    if(body?.status == "ok"){
                        _loadingStatus.value = LoadingStatus.DONE
                        _newsList.value = body.articles
                    } else {
                        _loadingStatus.value = LoadingStatus.ERROR
                    }
                }

                override fun onFailure(call: Call<Response>, t: Throwable) {
                    _loadingStatus.value = LoadingStatus.ERROR
                    Log.e("Exception", "${t.message}")
                    if(call.isCanceled){
                        call.cancel()
                    }
                }
            })
        }
    }
}