package com.example.modulea.presentation.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.modulea.domain.model.UniversityItem
import com.example.modulea.domain.usecase.GetUniversitiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UniversityViewModel @Inject constructor(
    private val getUniversitiesUseCase: GetUniversitiesUseCase
) : ViewModel() {
    private val TAG = "UniversityViewModel"

    private val _universities = MutableStateFlow<Result<List<UniversityItem>>>(Result.success(emptyList()))
    val universities: StateFlow<Result<List<UniversityItem>>> = _universities

    fun loadUniversities(country:String) {
        viewModelScope.launch {
            getUniversitiesUseCase(country).collect { result ->
                _universities.update {
                    result
                }
            }
        }
    }

    fun refreshUniversities(country: String) {
        viewModelScope.launch {
            _universities.update { Result.success(emptyList()) }
            loadUniversities(country)
        }
    }
}
