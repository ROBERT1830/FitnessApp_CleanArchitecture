package com.robertconstantindinescu.core.util


sealed class UiEvent {

    object Success: UiEvent()
    object NavigateUp: UiEvent()
    data class ShowSnackBar(val message: UiText): UiEvent()


}