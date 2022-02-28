package com.example.myapplication

class UserRepository(private val service: ReqResService = ReqResService.api) {
    suspend fun getUsers() = service.getUsers()
}