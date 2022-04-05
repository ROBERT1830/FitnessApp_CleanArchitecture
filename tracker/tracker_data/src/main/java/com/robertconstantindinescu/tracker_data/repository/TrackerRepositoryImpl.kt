package com.robertconstantindinescu.tracker_data.repository

import android.util.Log
import com.robertconstantindinescu.tracker_data.local.TrackerDao
import com.robertconstantindinescu.tracker_data.mapper.toTrackableFood
import com.robertconstantindinescu.tracker_data.mapper.toTrackedFood
import com.robertconstantindinescu.tracker_data.mapper.toTrackedFoodEntity
import com.robertconstantindinescu.tracker_data.remote.OpenFoodApi
import com.robertconstantindinescu.tracker_domain.model.TrackableFood
import com.robertconstantindinescu.tracker_domain.model.TrackedFood
import com.robertconstantindinescu.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate


class TrackerRepositoryImpl(
    private val dao: TrackerDao,
    private val api: OpenFoodApi
) : TrackerRepository {

    override suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<List<TrackableFood>> {

        return try {
            val searchDto = api.searchFood(
                query = query,
                page = page,
                pageSize = pageSize
            )


            Result.success(


                searchDto.products
                    //get only the products that its
                    .filter {

                        val calculatedCalories =
                            it.nutriments.carbohydrates100g * 4f + it.nutriments.proteins100g * 4f +
                                    it.nutriments.fat100g * 9f
                        val lowerBound = calculatedCalories * 0.99f //we tolerate 1% dow
                        val upperBound = calculatedCalories * 1.01f
                        it.nutriments.energyKcal100g in (lowerBound..upperBound)
                    }
                    .mapNotNull {
                        it.toTrackableFood()
                    }
            )


        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun insertTrackedFood(food: TrackedFood) {
        //use the maper
        dao.insertTrackedFood(food.toTrackedFoodEntity())
        Log.d("insertTrackedFood",  "---> Done")
    }

    override suspend fun deleteTrackedFood(food: TrackedFood) {
        dao.deleteTrackedFood(food.toTrackedFoodEntity())
    }

    override fun getFoodsForDate(localDate: LocalDate): Flow<List<TrackedFood>> {
        return dao.getFoodsForDate(
            day = localDate.dayOfMonth,
            month = localDate.monthValue,
            year = localDate.year
        ).map { entities ->
            entities.map {
                it.toTrackedFood()
            }

        }
    }
}