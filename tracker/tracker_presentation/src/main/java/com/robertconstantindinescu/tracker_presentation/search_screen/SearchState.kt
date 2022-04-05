package com.robertconstantindinescu.tracker_presentation.search_screen


data class SearchState(
    val query: String = "",
    val isHintVisible: Boolean = true,
    val isSearching: Boolean = false, //for the progress bar we show while the search is taking place

    val trackableFood: List<TrackableFoodUiState> = emptyList()
)
