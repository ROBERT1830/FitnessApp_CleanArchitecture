package com.robertconstantindinescu.tracker_domain.use_case

import com.robertconstantindinescu.core.domain.model.ActivityLevel
import com.robertconstantindinescu.core.domain.model.Gender
import com.robertconstantindinescu.core.domain.model.GoalType
import com.robertconstantindinescu.core.domain.model.UserInfo
import com.robertconstantindinescu.tracker_domain.model.MealType
import com.robertconstantindinescu.tracker_domain.model.TrackedFood
import dagger.multibindings.IntoMap
import java.util.prefs.Preferences
import kotlin.math.roundToInt


class CalculateMealNutrients(

    private val preferences: com.robertconstantindinescu.core.domain.preferences.Preferences
) {

    /**
     * This function will give us the list of tracked food for a given date. since we know from our db all
     * we gate is the food that we tracked for a given day.
     * Now this use case job is to take this and calculate this specific values so taht we can show taht in the
     * ui so we can separatetly show food for breakfast, diner and so on.
     *
     */
    operator fun invoke(trackedFoods: List<TrackedFood>): Result {




        val allNutrients = trackedFoods.groupBy {
            it.mealType}
            .mapValues { entry ->
                //key
                val type = entry.key
                //list
                val foods = entry.value
                MealNutrients(
                    carbs = foods.sumOf {
                        it.carbs
                    },
                    protein = foods.sumOf {
                        it.protein
                    },
                    fat = foods.sumOf { it.fat },
                    calories = foods.sumOf { it.calories },
                    mealType = type
                )
            }



        val totlCarbs = allNutrients.values.sumOf {
            it.carbs
        }
        val totalProtein = allNutrients.values.sumOf {
            it.protein
        }

        val totalFat = allNutrients.values.sumOf {
            it.fat
        }

        //esto es el valor de calorias de la top izquierda
        val totalCalories = allNutrients.values.sumOf {
            it.calories
        }



        val userInfo = preferences.loadUserInfo()

        val caloryGoal = dailyCaloryRequirement(userInfo)

        //divide by 4f because 1 g of carb has 4 calories
        val carbsGoal  = (caloryGoal * userInfo.carbRatio / 4f).roundToInt()
        val proteinGoal = (caloryGoal * userInfo.proteinRatio / 4f).roundToInt()
        val fatGoal = (caloryGoal * userInfo.fatRatio / 9f).roundToInt()

        //goal es lo del grafico.
        return Result(
            carbsGoal = carbsGoal,
            proteinGoal = proteinGoal,
            fatGoal = fatGoal,
            caloriesGoal = caloryGoal,
            totalCarbs = totlCarbs,
            totalProtein = totalProtein,
            totalFat = totalFat,
            totalCalories = totalCalories,
            melNutrients = allNutrients
        )



    }

    //te determina la cantidad de calorias que debe consumir un hombre o una mujer en funcion de su pero y altura y edad
    private fun bmr(userInfo: UserInfo): Int {
        return when(userInfo.gender) {
            is Gender.Male -> {
                (66.47f + 13.75f * userInfo.weight +
                        5f * userInfo.height - 6.75f * userInfo.age).roundToInt()
            }
            is Gender.Female ->  {
                (665.09f + 9.56f * userInfo.weight +
                        1.84f * userInfo.height - 4.67 * userInfo.age).roundToInt()
            }
        }
    }

    private fun dailyCaloryRequirement(userInfo: UserInfo): Int {
        val activityFactor = when(userInfo.activityLeve) {
            is ActivityLevel.Low -> 1.2f
            is ActivityLevel.Medium -> 1.3f
            is ActivityLevel.High -> 1.4f
        }
        val caloryExtra = when(userInfo.goalType) {
            is GoalType.LoseWeight -> -500
            is GoalType.KeepWeight -> 0
            is GoalType.GainWeight -> 500
        }

        return (bmr(userInfo) * activityFactor + caloryExtra).roundToInt()
    }





    data class MealNutrients(
        val carbs: Int,
        val protein: Int,
        val fat: Int,
        val calories: Int,
        val mealType: MealType
    )


    data class Result(
        val carbsGoal: Int,
        val proteinGoal: Int,
        val fatGoal: Int,
        val caloriesGoal: Int,
        val totalCarbs: Int,
        val totalProtein: Int,
        val totalFat: Int,
        val totalCalories: Int,
        val melNutrients: Map<MealType, MealNutrients>

    )
}