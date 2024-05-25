package com.example.modulea.domain.usecase

import android.util.Log
import com.example.modulea.domain.model.UniversityItem
import com.example.modulea.domain.repository.UniversityRepository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUniversitiesUseCase @Inject constructor(
    private val repository: UniversityRepository
) {
    private val TAG = "GetUniversitiesUseCase"
    suspend operator fun invoke(country:String): Flow<Result<List<UniversityItem>>> = flow {
        try {
            val universities = repository.getUniversities(country)
            if (universities.isNotEmpty()){
                emit(Result.success(universities))
            }
            else{
                emit(Result.failure(Exception()))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}
