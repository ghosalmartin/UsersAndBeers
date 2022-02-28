package com.example.myapplication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class BeerViewModel(
    private val getBeer: GetBeer = GetBeer()
): ViewModel() {

    var state: GetBeer.Data? by mutableStateOf(null)
        private set

    var errorMessage by mutableStateOf("")
        private set

    fun init(beerId: Int) {
        try {
            state = getBeer.execute(beerId)
        } catch (exception: Exception){
            errorMessage = exception.message.toString()
        }
    }
}