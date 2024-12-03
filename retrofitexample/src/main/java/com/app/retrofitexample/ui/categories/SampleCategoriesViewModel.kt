package com.app.retrofitexample.ui.categories

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.retrofitexample.data.models.Categories
import com.app.retrofitexample.data.respository.DefaultRepo
import com.app.retrofitexample.utils.Constants.EMPTY_STRING
import com.app.retrofitexample.data.models.CustomResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SampleCategoriesViewModel @Inject constructor(private val repo: DefaultRepo) : ViewModel() {

    val userIntent = Channel<MainIntent>(Channel.UNLIMITED)
    private val _state = MutableLiveData<UIState>()
    val state: LiveData<UIState> = _state

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it) {
                    is MainIntent.FetchCategories -> updateCategories()
                }
            }
        }
    }

    private fun updateCategories() {
        viewModelScope.launch {
            _state.value = UIState.Loading(View.VISIBLE)
            delay(2000)
            when (val result = repo.getCategories()) {
                is CustomResult.Success -> {
                    _state.value = UIState.Loading(View.GONE)
                    _state.value = UIState.Success(result.data)
                }

                is CustomResult.Error -> {
                    _state.value = UIState.Loading(View.GONE)
                    _state.postValue(UIState.Error(result.exception.message ?: EMPTY_STRING))
                }
            }
        }
    }

    sealed class UIState {
        data class Loading(val visibility: Int = View.GONE) : UIState()
        data class Success(val categories: List<Categories>) : UIState()
        data class Error(val message: String) : UIState()
    }


}
