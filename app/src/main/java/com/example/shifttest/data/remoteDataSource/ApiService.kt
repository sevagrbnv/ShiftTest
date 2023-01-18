package com.example.shifttest.data.remoteDataSource

import com.example.shifttest.data.remoteModel.BinData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/{bin}")
    suspend fun getResponce(
        @Path("bin") bin: String
    ): Response<BinData>?

    companion object {
        fun getBaseUrl() = "https://lookup.binlist.net"
    }
}