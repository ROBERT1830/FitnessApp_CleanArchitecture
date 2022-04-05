package com.robertconstantindinescu.core.data.preferences

import android.content.SharedPreferences
import com.robertconstantindinescu.core.domain.model.ActivityLevel
import com.robertconstantindinescu.core.domain.model.Gender
import com.robertconstantindinescu.core.domain.model.GoalType
import com.robertconstantindinescu.core.domain.model.UserInfo
import com.robertconstantindinescu.core.domain.preferences.Preferences

/**
 * Here we will define how will make the work with shared preferences behind the scenes.
 * like how to save, how to load each type of data.
 *
 * For that we will nedd the preferences object to save and read data from it
 */
class DefaultPreferences(


    private val sharedPref: SharedPreferences
) : Preferences {
    override fun saveGender(gender: Gender) {
        sharedPref.edit()
            .putString(Preferences.KEY_GENDER, gender.name).apply()

    }

    override fun saveAge(age: Int) {
        sharedPref.edit()
            .putInt(Preferences.KEY_AGE, age).apply()
    }

    override fun saveWeight(weight: Float) {
        sharedPref.edit()
            .putFloat(Preferences.KEY_WEIGHT, weight).apply()
    }

    override fun saveHeight(height: Int) {
        sharedPref.edit()
            .putInt(Preferences.KEY_HEIGHT, height).apply()
    }

    override fun saveActivityLevel(level: ActivityLevel) {
        sharedPref.edit()
            .putString(Preferences.KEY_ACTIVITY_LEVEL, level.name).apply()
    }

    override fun saveGoalType(type: GoalType) {
        sharedPref.edit()
            .putString(Preferences.KEY_GOAL_TYPE, type.name).apply()
    }

    override fun saveCarbRatio(ratio: Float) {
        sharedPref.edit().
                putFloat(Preferences.KEY_CARB_RATIO, ratio).apply()

    }

    override fun saveProteinRatio(ratio: Float) {

        sharedPref.edit()
            .putFloat(Preferences.KEY_PROTEIN_RATIO, ratio).apply()
    }

    override fun saveFatRatio(ratio: Float) {
        sharedPref.edit()
            .putFloat(Preferences.KEY_FAT_RATIO, ratio).apply()

    }


    override fun saveShouldShowOnboarding(shouldShow: Boolean) {

        sharedPref.edit()
            .putBoolean(Preferences.KEY_SHOULD_SHOW_ONBOARDING, shouldShow)
            .apply()
    }

    override fun loadShouldShowOnBoarding(): Boolean {
        return sharedPref.getBoolean(
            Preferences.KEY_SHOULD_SHOW_ONBOARDING, true //by default we wanto to show onboarding
        )
    }

    /**
     * Load the data from sharedpref and combine it with a single uSERiNFO OBJECT
     * So for that we will get all the variable sfrom shared pref and store them in variables
     *
     */
    override fun loadUserInfo(): UserInfo {

        val age = sharedPref.getInt(Preferences.KEY_AGE, -1)
        val height = sharedPref.getInt(Preferences.KEY_HEIGHT, -1)
        val weight = sharedPref.getFloat(Preferences.KEY_WEIGHT, -1F)
        val genderString = sharedPref.getString(Preferences.KEY_GENDER, null)
        val activityLevelString = sharedPref.getString(Preferences.KEY_ACTIVITY_LEVEL, null)
        val goalType = sharedPref.getString(Preferences.KEY_GOAL_TYPE, null)
        val carbRatio = sharedPref.getFloat(Preferences.KEY_CARB_RATIO, -1f)
        val proteinRatio = sharedPref.getFloat(Preferences.KEY_PROTEIN_RATIO, -1f)
        val fatRatio = sharedPref.getFloat(Preferences.KEY_PROTEIN_RATIO, -1f)

        //the UserInfo object will not be saved in database. This is to create an object that we wil use after in some classes
        return UserInfo(
            gender = Gender.fromString(genderString?: "male"),
            age = age,
            weight = weight,
            height = height,
            activityLeve = ActivityLevel.fromString(activityLevelString?:"medium"),
            goalType = GoalType.fromString(goalType?: "keep_weight"),
            carbRatio = carbRatio,
            proteinRatio = proteinRatio,
            fatRatio = fatRatio

        )


    }
}