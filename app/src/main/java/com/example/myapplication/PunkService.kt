package com.example.myapplication

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.http.GET

interface PunkService {

    @GET(BEER_PATH)
    suspend fun getBeers(): List<Beer>

    @Serializable
    data class Beer(
        val id: Int,
        val name: String,
        val tagline: String,
        @SerialName("first_brewed") val firstBrewed: String,
        val description: String,
        @SerialName("image_url") val imageUrl: String,
        val abv: Float?,
        val ibu: Float?,
        @SerialName("target_fg") val targetFg: Float,
        @SerialName("target_og") val targetOg: Float,
        val ebc: Float?,
        val srm: Float?,
        val ph: Float?,
        @SerialName("attenuation_level") val attenuationLevel: Float,
        val volume: Measurement,
        @SerialName("boil_volume") val boilVolume: Measurement,
        val method: Method,
        val ingredients: Ingredients,
        @SerialName("food_pairing") val foodPairing: List<String>,
        @SerialName("brewers_tips") val brewersTips: String,
        @SerialName("contributed_by") val contributedBy: String,
    ) {

        @Serializable
        data class Measurement(val value: Double, val unit: String)

        @Serializable
        data class Method(
            @SerialName("mash_temp") val mashTemp: List<MashTemp>,
            val fermentation: Fermentation,
            val twist: String? = null
        ) {

            @Serializable
            data class MashTemp(val temp: Measurement, val duration: Int?)

            @Serializable
            data class Fermentation(val temp: Measurement)
        }

        @Serializable
        data class Ingredients(
            val malt: List<Malt>,
            val hops: List<Hops>,
            val yeast: String,
        ) {

            @Serializable
            data class Malt(
                val name: String,
                val amount: Measurement,
            )

            @Serializable
            data class Hops(
                val name: String,
                val amount: Measurement,
                val add: String,
                val attribute: String,
            )
        }
    }

    companion object {
        private val lenientJson = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }

        val api: PunkService by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(lenientJson.asConverterFactory(MediaType.get("application/json")))
                .build()
                .create(PunkService::class.java)
        }
    }
}