package com.robertconstantindinescu.tracker_presentation.search_screen

import com.robertconstantindinescu.tracker_domain.model.MealType
import com.robertconstantindinescu.tracker_domain.model.TrackableFood
import java.time.LocalDate

/**
 * The things that the user can do
 */
sealed class SearchEvent{
    data class OnQueryChange(val query: String): SearchEvent() //change entered text

    object OnSearch: SearchEvent()
    data class OnToogleTrackableFood(val food: TrackableFood):SearchEvent()

    data class OnAmountForFoodChange(
        val food: TrackableFood,
        val amount: String
        ): SearchEvent()
    data class OnTrackFoodClick(
        val food: TrackableFood,
        val mealType: MealType,
        val date: LocalDate
    ):SearchEvent()
    data class OnSearchFocusChange(val isFocused: Boolean): SearchEvent()

}
