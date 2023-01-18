package com.example.shifttest.di

import android.content.Context
import androidx.room.Room
import com.example.shifttest.data.RepositoryImpl
import com.example.shifttest.data.localDataSource.AppDataBase
import com.example.shifttest.data.localDataSource.BinDao
import com.example.shifttest.data.remoteDataSource.ApiService
import com.example.shifttest.domain.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
}