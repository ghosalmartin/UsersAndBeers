package com.example.myapplication

class GetUserList(private val repository: UserRepository = UserRepository()) {

    data class Data(val name: String, val avatarUrl: String)

    suspend fun execute() = repository.getUsers().data.map {
        Data(
            name = "${it.firstName} ${it.lastName}",
            avatarUrl = it.avatar
        )
    }
}