package com.example.modulea.domain.repository

import com.example.modulea.domain.model.UniversityItem

interface UniversityRepository {
    suspend fun getUniversities(country:String): List<UniversityItem>
}