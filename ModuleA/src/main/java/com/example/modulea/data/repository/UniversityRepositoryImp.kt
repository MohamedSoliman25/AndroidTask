package com.example.modulea.data.repository

import android.util.Log
import com.example.modulea.data.local.UniversityDao
import com.example.modulea.data.remote.ApiService
import com.example.modulea.domain.model.UniversityItem
import com.example.modulea.domain.repository.UniversityRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class UniversityRepositoryImp @Inject constructor(private val apiService: ApiService,
                              private val universityDao: UniversityDao
                            ):UniversityRepository {
    private val TAG = "UniversityRepositoryImp"
    override suspend fun getUniversities(country: String): List<UniversityItem> {
        return try {
            val universities = apiService.getUniversities(country)
            universityDao.insertAll(universities)
            universities
        } catch (e: Exception) {
            universityDao.getAllUniversities()
            .firstOrNull() ?: throw e
        }
    }

}