package com.example.shifttest.data.remoteModel

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bins")
data class BinData(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val request: String = "",
    @Embedded(prefix = "bank_")
    val bank: Bank,
    val brand: String = "-",
    @Embedded(prefix = "country_")
    val country: Country,
    @Embedded(prefix = "number_")
    val number: Number,
    val prepaid: Boolean = false,
    val scheme: String = "-",
    val type: String = "-"
)