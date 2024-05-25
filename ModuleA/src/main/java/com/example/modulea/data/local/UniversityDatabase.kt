package com.example.modulea.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.modulea.domain.model.UniversityItem

@Database(entities = [UniversityItem::class], version = 1)
@TypeConverters(Converters::class)
abstract class UniversityDatabase : RoomDatabase(){
    abstract fun universityDao(): UniversityDao
}