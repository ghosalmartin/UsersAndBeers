package com.example.myapplication

class BeerRepository(
    private val service: PunkService = PunkService.api,
    private val beers: MutableList<PunkService.Beer> = mutableListOf()
) {

    companion object {
        val instance by lazy {
            BeerRepository()
        }
    }

    suspend fun getBeers() = service.getBeers().also {
        beers.clear()
        beers.addAll(it)
    }

    fun getBeerById(id: Int): PunkService.Beer =
        beers.find { it.id == id }
            ?: throw IllegalStateException("You somehow have an id to a beer that doesn't exist")
}