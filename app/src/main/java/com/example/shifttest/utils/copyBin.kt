package com.example.shifttest.utils

import com.example.shifttest.model.Bank
import com.example.shifttest.model.BinData
import com.example.shifttest.model.Country
import com.example.shifttest.model.Number

fun copyBin(bin: BinData?, binString: String) = BinData(
    request = binString,
    bank = bin?.bank ?: Bank(),
    brand = bin?.brand ?: "-",
    country = bin?.country ?: Country(),
    number = bin?.number ?: Number(),
    prepaid = bin?.prepaid ?: false,
    scheme = bin?.scheme ?: "-",
    type = bin?.type ?: "-"
)