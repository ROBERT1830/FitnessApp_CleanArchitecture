package com.robertconstantindinescu.tracker_presentation.search_screen

import com.robertconstantindinescu.tracker_domain.model.TrackableFood


data class TrackableFoodUiState(
    val food: TrackableFood,
    val isExpanded: Boolean = false,
    val amount: String  = ""
)
