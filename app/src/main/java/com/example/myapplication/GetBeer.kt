package com.example.myapplication

class GetBeer(private val beerRepository: BeerRepository = BeerRepository.instance) {

    data class Data(
        val name: String,
        val imageUrl: String,
        val hops: List<String>,
        val malt: List<String>,
        val yeast: String
    )

    fun execute(id: Int): Data = beerRepository.getBeerById(id).run {
        Data(
            name = name,
            imageUrl = imageUrl,
            malt = ingredients.malt.map {  "${it.name} - ${it.amount.value} ${it.amount.unit}" },
            hops = ingredients.hops.map { "${it.name} - ${it.amount.value} ${it.amount.unit} - ${it.add} - ${it.attribute}" },
            yeast = ingredients.yeast
        )
    }
}