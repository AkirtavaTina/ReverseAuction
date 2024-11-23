package com.android.example.domain.repository

import com.android.example.domain.model.Category
import com.android.example.domain.model.Subcategory
import kotlinx.coroutines.flow.Flow

interface AuctionsRepository {
    suspend fun getCategories(cursor: Int = 0, limit: Int? = null): Flow<List<Category>>
    suspend fun getSuperCategories(): Flow<List<Category>>
    suspend fun getSubCategories(categoryId: Int): List<Subcategory>
}