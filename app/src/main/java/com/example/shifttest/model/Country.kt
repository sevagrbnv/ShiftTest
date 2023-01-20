package com.example.shifttest.model

data class Country(
    val alpha2: String = "-",
    val currency: String = "-",
    val emoji: String = "-",
    val latitude: Int = 0,
    val longitude: Int = 0,
    val name: String = "-",
    val numeric: String = "-"
)