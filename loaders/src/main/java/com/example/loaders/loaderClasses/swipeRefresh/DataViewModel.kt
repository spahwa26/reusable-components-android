package com.example.loaders.loaderClasses.swipeRefresh

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DataViewModel : ViewModel() {

    private val _data = MutableLiveData<String?>(null)
    val data: LiveData<String?> = _data

    fun fetchData() = viewModelScope.launch(Dispatchers.IO) {
        delay(5000)
        _data.postValue("Delayed data")
    }
}