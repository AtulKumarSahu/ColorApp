package com.example.colorapp.ui.theme.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ColorEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val color: String,
    val timestamp: Long,
    val synced: Boolean = false
)