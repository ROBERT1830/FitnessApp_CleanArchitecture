package com.robertconstantindinescu.core.domain.model

/**
 * This sealed class, its variabkle it will be saved in shared preferences.
*/
sealed class GoalType(val name:String) {
    object LoseWeight: GoalType("lose_weight") //se instancia automaticamente con el nombre
    object KeepWeight: GoalType("keep_weight")
    object GainWeight: GoalType("gain_weight")

    /**
     * Define a comopanion object to take the string and return such an object
     */
    companion object{
        fun fromString(name:String): GoalType{
            return when(name){
                "lose_weight" -> LoseWeight
                "keep_weight" -> KeepWeight
                "gain_weight" -> GainWeight
                else -> KeepWeight //this will never happen
            }
        }

    }
}
