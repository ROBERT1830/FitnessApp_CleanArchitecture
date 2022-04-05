package com.robertconstantindinescu.onboarding_presentation.activity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robertconstantindinescu.core.domain.model.ActivityLevel
import com.robertconstantindinescu.core.domain.preferences.Preferences
import com.robertconstantindinescu.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val preferences: Preferences //interface
): ViewModel()  {

    var selectedActivityLevel by mutableStateOf<ActivityLevel>(ActivityLevel.Medium)
        private set //only change the value from this viewModel


    private val _uiEvent = Channel<UiEvent> ()
    val uiEvent = _uiEvent.receiveAsFlow()
    fun onActivityLevelSelect(activityLevel: ActivityLevel){
        selectedActivityLevel = activityLevel
    }

    fun onNextClick(){
        viewModelScope.launch {
            preferences.saveActivityLevel(selectedActivityLevel)
            _uiEvent.send(UiEvent.Success)
        }


    }
}