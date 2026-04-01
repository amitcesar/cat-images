package com.github.amitcesar.cat_images.model

import kotlinx.serialization.Serializable

@Serializable
data class CatImage(
    val id: String,
    val url: String,
    val width: Int,
    val height: Int
)
