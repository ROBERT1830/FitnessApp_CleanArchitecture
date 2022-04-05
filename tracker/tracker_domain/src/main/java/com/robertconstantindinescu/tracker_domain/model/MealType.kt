package com.robertconstantindinescu.tracker_domain.model


sealed class MealType(val name: String){

    object BreakFast: MealType("breakfast")
    object Lunch: MealType("lunch")
    object Dinner: MealType("dinner")
    object Snack: MealType("snack")

    //function to be able to pars such a object from a string

    companion object {
        fun fromString(name: String): MealType{
            return when(name){
                "breakfast" -> BreakFast
                "lunch" -> Lunch
                "dinner" -> Dinner
                "snack" -> Snack
                else -> Lunch
            }
        }
    }
}
