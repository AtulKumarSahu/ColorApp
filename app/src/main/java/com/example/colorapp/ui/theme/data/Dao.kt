package com.example.colorapp.ui.theme.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ColorDao {
    @Query("SELECT * FROM ColorEntity")
    fun getAllColors(): Flow<List<ColorEntity>>

    @Insert
    suspend fun insertColor(color: ColorEntity)

    @Update
    suspend fun updateColor(color: ColorEntity)
}