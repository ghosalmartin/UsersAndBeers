package com.example.myapplication

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.http.GET

interface ReqResService {

    @GET(USER_PATH)
    suspend fun getUsers(): UserResponse

    @Serializable
    data class UserResponse(
        val data: List<Data>
    ) {
        @Serializable
        data class Data(
            val id: Int,
            val email: String,
            @SerialName("first_name") val firstName: String,
            @SerialName("last_name") val lastName: String,
            val avatar: String,
        )
    }

    companion object {
        private val lenientJson = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }

        val api by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(lenientJson.asConverterFactory(MediaType.get("application/json")))
                .build()
                .create(ReqResService::class.java)
        }
    }

}