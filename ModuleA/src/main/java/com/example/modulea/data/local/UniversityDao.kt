package com.example.modulea.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.modulea.domain.model.UniversityItem
import kotlinx.coroutines.flow.Flow

@Dao
interface UniversityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(universities: List<UniversityItem>)

    @Query("SELECT * FROM universities")
    fun getAllUniversities(): Flow<List<UniversityItem>>
}