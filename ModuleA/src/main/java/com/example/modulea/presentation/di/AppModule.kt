package com.example.modulea.presentation.di

import com.example.modulea.data.local.UniversityDao
import com.example.modulea.data.local.UniversityDatabase
import com.example.modulea.data.remote.ApiService
import com.example.modulea.data.repository.UniversityRepositoryImp
import com.example.modulea.domain.repository.UniversityRepository
import com.example.modulea.domain.usecase.GetUniversitiesUseCase
import com.example.modulea.util.Utils
import dagger.hilt.android.qualifiers.ApplicationContext
import android.content.Context
import androidx.room.Room
import com.example.modulea.util.LoggingInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): LoggingInterceptor {
        return LoggingInterceptor()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: LoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(Utils.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): UniversityDatabase =
        Room.databaseBuilder(context, UniversityDatabase::class.java, "app_database")
            .build()

    @Provides
    @Singleton
    fun provideUniversityDao(universityDatabase: UniversityDatabase): UniversityDao = universityDatabase.universityDao()
    @Provides
    @Singleton
    fun provideUniversityRepository(
        apiService: ApiService,
        universityDao: UniversityDao
    ): UniversityRepository = UniversityRepositoryImp(apiService, universityDao)

    @Provides
    @Singleton
    fun provideGetUniversitiesUseCase(
        repository: UniversityRepository
    ): GetUniversitiesUseCase = GetUniversitiesUseCase(repository)
}
