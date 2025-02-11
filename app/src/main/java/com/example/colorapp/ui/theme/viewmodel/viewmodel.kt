package com.example.colorapp.ui.theme.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.colorapp.ui.theme.Repo.ColorRepository
import com.example.colorapp.ui.theme.data.ColorEntity
import kotlinx.coroutines.launch

class ColorViewModel(private val repository: ColorRepository) : ViewModel() {
    val colors: LiveData<List<ColorEntity>> = repository.getColors().asLiveData()

    fun addColor() {
        val newColor = ColorEntity(
            color = "#" + List(6) { "0123456789ABCDEF".random() }.joinToString(""),
            timestamp = System.currentTimeMillis()
        )
        viewModelScope.launch { repository.addColor(newColor) }
    }

    fun syncColors() {
        viewModelScope.launch { repository.syncColors() }
    }
}