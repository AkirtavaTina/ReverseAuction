package com.android.example.domain.usecase

import com.android.example.domain.model.Category
import com.android.example.domain.model.Subcategory
import com.android.example.domain.repository.AuctionsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuctionsUseCase@Inject constructor(
    private val auctionsRepository: AuctionsRepository
)  {
    suspend fun getCategories(cursor: Int = 0, limit: Int? = null): Flow<List<Category>>{
        return auctionsRepository.getCategories(cursor, limit)
    }

    suspend fun getSuperCategories() = auctionsRepository.getSuperCategories()

    suspend fun getSubCategories(categoryId: Int): List<Subcategory> {
        return auctionsRepository.getSubCategories(categoryId)
    }
}