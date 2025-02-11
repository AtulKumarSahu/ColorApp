package com.example.colorapp.ui.theme.Repo

import com.example.colorapp.ui.theme.data.ColorDao
import com.example.colorapp.ui.theme.data.ColorEntity
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.flow.first

class ColorRepository(
    private val colorDao: ColorDao,
    private val firebaseDatabase: DatabaseReference
) {
    fun getColors() = colorDao.getAllColors()

    suspend fun addColor(color: ColorEntity) {
        colorDao.insertColor(color)
    }

    suspend fun syncColors() {
        val unsyncedColors = colorDao.getAllColors().first().filter { !it.synced }
        for (color in unsyncedColors) {
            firebaseDatabase.child("colors").push().setValue(color)
            colorDao.updateColor(color.copy(synced = true))
        }
    }
}