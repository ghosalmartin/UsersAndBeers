package com.example.myapplication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class BeerListViewModel(private val getBeerList: GetBeerList = GetBeerList()) : ViewModel() {

    private val mutableStateOf = mutableStateListOf<GetBeerList.Data>()
    val state: List<GetBeerList.Data> = mutableStateOf

    var errorMessage by mutableStateOf("")
        private set

    fun init() {
        viewModelScope.launch {
            errorMessage = ""
            try {
                mutableStateOf.clear()
                mutableStateOf.addAll(getBeerList.execute())
            } catch (exception: Exception) {
                errorMessage = exception.message.toString()
            }
        }
    }
}