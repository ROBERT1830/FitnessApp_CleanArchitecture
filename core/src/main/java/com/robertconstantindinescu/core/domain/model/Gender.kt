package com.robertconstantindinescu.core.domain.model

/**
 * This sealed class, its variabkle it will be saved in shared preferences. So we have
 * to have a variable string that defines both os genders because in shared preferences
 * we cant save objects
 */
sealed class Gender(val name:String) {
    object Male: Gender("male")
    object Female: Gender("female")

    /**
     * Define a comopanion object to take the string and return such an object
     */
    companion object{
        fun fromString(name:String): Gender{
            return when(name){
                "male" -> Male
                "female" -> Female
                else -> Female //this will never happen
            }
        }

    }
}
