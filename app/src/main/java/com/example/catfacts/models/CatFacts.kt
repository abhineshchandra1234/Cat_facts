package com.example.catfacts.models


import com.google.gson.annotations.SerializedName

data class CatFacts(
    @SerializedName("fact")
    val fact: String = "",
    @SerializedName("length")
    val length: Int = 0
)