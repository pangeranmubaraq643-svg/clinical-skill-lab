package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CaseDao {
    @Query("SELECT * FROM case_history ORDER BY timestamp DESC")
    fun getAllCases(): Flow<List<CaseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCase(caseEntity: CaseEntity)

    @Query("DELETE FROM case_history WHERE id = :id")
    suspend fun deleteCaseById(id: Int)

    @Query("DELETE FROM case_history")
    suspend fun clearAllCases()
}
