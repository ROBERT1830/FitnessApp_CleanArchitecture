package com.robertconstantindinescu.tracker_data.mapper

import android.util.Log
import com.robertconstantindinescu.tracker_data.local.entity.TrackedFoodEntity
import com.robertconstantindinescu.tracker_domain.model.MealType
import com.robertconstantindinescu.tracker_domain.model.TrackedFood
import java.time.LocalDate

fun TrackedFoodEntity.toTrackedFood(): TrackedFood {
    return TrackedFood(
        name = name,
        carbs = carbs,
        protein = protein,
        fat = fat,
        imageUrl = imageUrl,
        mealType = MealType.fromString(type),
        amount = amount,
        date = LocalDate.of(year, month, dayOfMonth),
        calories = calories,
        id = id
    )
}


fun TrackedFood.toTrackedFoodEntity(): TrackedFoodEntity{
    Log.d("MapperToEntity", "---> Called")
    return TrackedFoodEntity(
        name = name,
        carbs = carbs,
        protein = protein,
        fat = fat,
        imageUrl = imageUrl,
        type = mealType.name, //accedes al y con la variable directamtente al nombre string
        amount = amount,
        dayOfMonth = date.dayOfMonth, //is a get from kotlin in whcih you get the day of month from locaDate object you have
        month = date.monthValue,
        year = date.year,
        calories = calories,
        id = id

    )
}