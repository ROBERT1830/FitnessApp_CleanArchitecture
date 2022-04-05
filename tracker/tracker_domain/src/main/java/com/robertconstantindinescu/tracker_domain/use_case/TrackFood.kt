package com.robertconstantindinescu.tracker_domain.use_case

import com.robertconstantindinescu.tracker_domain.model.MealType
import com.robertconstantindinescu.tracker_domain.model.TrackableFood
import com.robertconstantindinescu.tracker_domain.model.TrackedFood
import com.robertconstantindinescu.tracker_domain.repository.TrackerRepository
import java.time.LocalDate
import kotlin.math.roundToInt

class TrackFood(
    private val repository: TrackerRepository
) {

    /**
     * Here we need the object Trackabalefood that we want to track and insert it into the db
     * So what is giing here is taht we will have from the ui some data. with that data we will create
     * an object Trackable food that now can be mapped to an entity and saved in room
     *
     * The thing is that to create the entity object we need more data thatn the
     * Trackable food has inside it. So we need aditional parameters to pass
     */
    suspend operator fun invoke(
        food: TrackableFood,
        amount: Int,
        mealType: MealType,
        date: LocalDate
    ){

        /**
         * El trackabel food, que es el que obtenemos a aparitr de la api has less data thatn
         * the trackedFoodEntitity. So wee need aditional data.
         *
         * Here what is going on is that we will insert a trackedFood which has all the data needed
         * but in turns the repo will mao it to a entitiy.
         */


        repository.insertTrackedFood(

            TrackedFood(
                name = food.name,
                //This is the total amount of carbs. For taht
                //divide to know how many carbs have that food in one gram and then * amount (how many grams we actually tracked or want to eat of that food)
                carbs = ((food.carbsPer100g / 100f) * amount).roundToInt(), //total carbs that we track for this food
                protein = ((food.proteinPer100g / 100f) * amount).roundToInt(),
                fat = ((food.fatPer100g / 100f) * amount).roundToInt(),
                calories = ((food.caloriesPer100g / 100f) * amount).roundToInt(),
                imageUrl = food.imageUrl,
                mealType = mealType,
                amount = amount,
                date = date
            //here not oass id because it is geberate by room
            )
        )

    }
}