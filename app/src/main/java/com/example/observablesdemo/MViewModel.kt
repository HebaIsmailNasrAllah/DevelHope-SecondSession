package com.example.observablesdemo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MViewModel : ViewModel() {

    private val _mLiveData: MutableLiveData<String> = MutableLiveData("Hello LiveData")

    val liveData: LiveData<String>
        get() = _mLiveData

    private val _mSharedFlow: MutableSharedFlow<String> = MutableSharedFlow()
    val sharedFlow: SharedFlow<String>
        get() = _mSharedFlow

    private val _mStateFlow: MutableStateFlow<String> = MutableStateFlow("Hello StateFlow")
    val stateFlow: StateFlow<String>
        get() = _mStateFlow


    fun mutateLiveData() {
        _mLiveData.value = "Update - LiveData"
    }

    fun mutateSharedFlow() {
        viewModelScope.launch {
            _mSharedFlow.emit("Update- SharedFlow")
        }
    }

    fun mutateStateFlow() {
        viewModelScope.launch {
            _mStateFlow.emit("Update - StateFlow")
        }
    }

    //naming convention - feature related
    fun mutateFlow(): Flow<String> {
        return flow<String> {
            delay(100)
            emit("Update- Flow")
        }
    }

}