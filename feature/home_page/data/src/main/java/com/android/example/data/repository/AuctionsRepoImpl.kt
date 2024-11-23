package com.android.example.data.repository

import com.android.example.data.AuctionsApiService
import com.android.example.domain.model.Category
import com.android.example.domain.model.Subcategory
import com.android.example.domain.repository.AuctionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Named

class AuctionsRepoImpl @Inject constructor(
    @Named("auctionsApi") private val auctionsApiService: AuctionsApiService) :
    AuctionsRepository {
    override suspend fun getCategories(cursor: Int, limit: Int?): Flow<List<Category>> = flow {
        val response = auctionsApiService.getCategories(cursor, limit)
        if (response.isSuccessful) {
            response.body()?.let { categories ->
                emit(categories)
            }
        } else {
            throw Exception("Failed to load categories: ${response.message()}")
        }
    }

    override suspend fun getSuperCategories(): Flow<List<Category>> = flow {
        val categories = auctionsApiService.getSuperCategories()
        emit(categories)
    }

    override suspend fun getSubCategories(categoryId: Int): List<Subcategory> {
        return auctionsApiService.getSubCategories(categoryId)
    }
}