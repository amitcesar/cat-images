package com.github.amitcesar.cat_images.api

import com.github.amitcesar.cat_images.model.CatImage
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class CatApiService {

    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun fetchCats(limit: Int = 20): List<CatImage> {
        return client.get("https://api.thecatapi.com/v1/images/search?limit=$limit").body()
    }

    fun close() {
        client.close()
    }
}
