package com.robertconstantindinescu.tracker_presentation.search_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.robertconstantindinescu.core.util.UiEvent
import com.robertconstantindinescu.core_ui.LocalSpacing
import com.robertconstantindinescu.tracker_domain.model.MealType
import com.robertconstantindinescu.tracker_presentation.R
import com.robertconstantindinescu.tracker_presentation.search_screen.components.SearchTextField
import com.robertconstantindinescu.tracker_presentation.search_screen.components.TrackableFoodItem
import java.time.LocalDate

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    scaffoldState: ScaffoldState, //in case we wanto to show snackbar
    //get the parameters
    mealName: String,
    dayOfMonth: Int,
    month: Int,
    year: Int,
    onNavigateUp: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val state = viewModel.state
    val context = LocalContext.current //for context to get the snackabar
    //variable to hide the keyboard when we want to do taht
    val keyBoardController = LocalSoftwareKeyboardController.current //this is nullable
    LaunchedEffect(key1 = keyBoardController) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {

                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                    keyBoardController?.hide()
                }
                is UiEvent.NavigateUp -> {
                    onNavigateUp()
                }
                else -> Unit
            }

        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                spacing.spaceMedium
            )
    ) {
        Text(
            text = stringResource(id = R.string.add_meal, mealName),
            style = MaterialTheme.typography.h2
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        SearchTextField(
            text = state.query,
            onValueChange = {
                viewModel.onEvent(SearchEvent.OnQueryChange(it))
            },
            shouldShowHint = state.isHintVisible,
            onSearch = {
                keyBoardController?.hide()
                viewModel.onEvent(SearchEvent.OnSearch)

            },
            onFocusChanged = {
                viewModel.onEvent(SearchEvent.OnSearchFocusChange(it.isFocused))
            }
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.trackableFood) { food ->
                TrackableFoodItem(
                    trackableFoodUiState = food,
                    onClick = {
                        viewModel.onEvent(SearchEvent.OnToogleTrackableFood(food.food))
                    },
                    onAmountChange = {
                        viewModel.onEvent(
                            SearchEvent.OnAmountForFoodChange(
                                food = food.food,
                                amount = it
                            )
                        )
                    },
                    onTrack = {
                        keyBoardController?.hide()
                        viewModel.onEvent(
                            SearchEvent.OnTrackFoodClick(
                                food = food.food,
                                mealType = MealType.fromString(mealName),
                                date = LocalDate.of(year, month, dayOfMonth)
                            )
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )

            }
        }

    }

    Box(modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center) {
        when{
            state.isSearching -> CircularProgressIndicator()
            state.trackableFood.isEmpty() -> {
                Text(
                    text = stringResource(id = R.string.no_results),
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center
                )

            }
        }

    }

}





































