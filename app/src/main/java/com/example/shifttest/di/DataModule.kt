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
class DataModule {
    @Provides
    fun provideBaseUrl() = ApiService.getBaseUrl()

    @Provides
    fun provideLogging() = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    fun provideOkHttpClient() = OkHttpClient.Builder()
        .addInterceptor(provideLogging())
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String): ApiService =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClient())
            .build()
            .create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideRepositoty(
        apiService: ApiService,
        binDao: BinDao
    ): Repository {
        return RepositoryImpl(
            apiService = apiService,
            binDao = binDao
        )
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            "bin.db"
        ).build()

    @Provides
    fun provideDao(appDatabase: AppDataBase): BinDao {
        return appDatabase.binDao()
    }
}