package com.robertconstantindinescu.tracker_presentation.tracker_overview_screen

import com.robertconstantindinescu.tracker_domain.model.TrackedFood
import java.time.LocalDate

/**
 * Here we not have all the states, all those variales taht will change
 */
data class TrackerOverviewState(
    val totalCarbs: Int = 0,
    val totalProtein: Int = 0,
    val totalFat: Int = 0,
    val totalCalories: Int = 0,
    val carbsGoal: Int = 0,
    val proteinGoal: Int = 0,
    val fatGoal: Int = 0,
    val caloriesGoal: Int = 0,
    val date: LocalDate = LocalDate.now(),
    //list of tracked fodd for that given day
    val trackedFoods: List<TrackedFood> = emptyList(),
    //this are the icons
    val meals: List<Meal> = defaultMeals


)
