package com.example.modulea.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable


@Entity(tableName = "universities")
data class UniversityItem(
    val alpha_two_code: String,
    val country: String,
    val domains: List<String>,
    @PrimaryKey
    val name: String,
    @SerializedName("state-province")
    val state_province: String?,
    val web_pages: List<String>
): Serializable