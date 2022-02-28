package com.example.myapplication

class GetBeerList(private val repository: BeerRepository = BeerRepository.instance) {

    data class Data(val id: Int, val name: String, val description: String, val avatarUrl: String)

    suspend fun execute() = repository.getBeers().map {
        Data(
            id = it.id,
            name = it.name,
            description = it.description,
            avatarUrl = it.imageUrl,
        )
    }
}