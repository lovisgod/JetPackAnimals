package com.example.animals.model

import com.google.gson.annotations.SerializedName
import java.net.ProtocolFamily


// a data class does not need a body
data class Animal (
    val name : String?,
    val taxonomy: Taxonomy?,
    val speed: Speed?,
    val diet: String?,

    @SerializedName("lifespan")
    val lifeSpan: String?,

    @SerializedName("image")
    val imageUrl: String?
)

data class ApiKey (
    val message: String?,
    val key: String?,
)

data class Taxonomy (
    val kingdom: String?,
    val order: String?,
    val family: String?
)

data class Speed(
    val metric: String?,
    val imperial: String?
)