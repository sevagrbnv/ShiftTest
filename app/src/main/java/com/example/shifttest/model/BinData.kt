package com.example.shifttest.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bins")
data class BinData(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val request: String = "",
    @Embedded(prefix = "bank_")
    val bank: Bank = Bank(),
    val brand: String = "-",
    @Embedded(prefix = "country_")
    val country: Country = Country(),
    @Embedded(prefix = "number_")
    val number: Number = Number(),
    val prepaid: Boolean = false,
    val scheme: String = "-",
    val type: String = "-"
)