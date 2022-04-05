package com.robertconstantindinescu.tracker_presentation.tracker_overview_screen

import androidx.annotation.DrawableRes
import com.robertconstantindinescu.core.util.UiText
import com.robertconstantindinescu.tracker_domain.model.MealType
import com.robertconstantindinescu.tracker_presentation.R


data class Meal(
    val name: UiText,
    @DrawableRes val drawableRes: Int,
    val mealTYpe: MealType,
    val carbs: Int = 0,
    val protein: Int = 0,
    val fat: Int = 0,
    val calories: Int = 0,
    val isExpanded: Boolean = false
)

//define some default meals to display in the ui

val defaultMeals = listOf(
    Meal(
        name = UiText.StringResource(R.string.breakfast),
        drawableRes = R.drawable.ic_burger,
        mealTYpe = MealType.BreakFast
    ),
    Meal(
        name = UiText.StringResource(R.string.lunch),
        drawableRes = R.drawable.ic_lunch,
        mealTYpe = MealType.Lunch
    ),
    Meal(
        name = UiText.StringResource(R.string.dinner),
        drawableRes = R.drawable.ic_dinner,
        mealTYpe = MealType.Dinner
    ),
    Meal(
        name = UiText.StringResource(R.string.snacks),
        drawableRes = R.drawable.ic_snack,
        mealTYpe = MealType.Snack
    ),



)