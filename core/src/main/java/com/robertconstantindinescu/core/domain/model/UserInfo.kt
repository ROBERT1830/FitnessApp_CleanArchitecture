package com.robertconstantindinescu.core.domain.model

import android.util.FloatMath


data class UserInfo(
    val gender: Gender,
    val age: Int,
    val weight: Float,
    val height: Int,
    val activityLeve: ActivityLevel,
    val goalType: GoalType,
    val carbRatio: Float,
    val proteinRatio: Float,
    val fatRatio: Float
)
