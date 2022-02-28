package com.example.myapplication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel(private val getUserList: GetUserList = GetUserList()) : ViewModel() {

    private val mutableStateOf = mutableStateListOf<GetUserList.Data>()
    val state: List<GetUserList.Data> = mutableStateOf

    var errorMessage by mutableStateOf("")

    fun init() {
        viewModelScope.launch {
            errorMessage = ""
            try {
                mutableStateOf.clear()
                mutableStateOf.addAll(getUserList.execute())
            } catch (exception: Exception) {
                errorMessage = exception.message.toString()
            }
        }
    }
}