package com.robertconstantindinescu.tracker_presentation.tracker_overview_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.toLowerCase
import androidx.hilt.navigation.compose.hiltViewModel
import com.robertconstantindinescu.core_ui.LocalSpacing
import com.robertconstantindinescu.tracker_presentation.R
import com.robertconstantindinescu.tracker_presentation.tracker_overview_screen.components.*

@Composable
fun TrackerOverviewScreen(
    onNavigateToSearch: (String, Int, Int, Int) -> Unit,
    viewModel: TrackerOverviewViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val state = viewModel.state
    val context = LocalContext.current


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = spacing.spaceMedium)
    ) {
        //single item for the header
        item {
            NutrientsHeader(state = state)
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            DaySelector(
                date = state.date,
                onPreviousDayClick = {
                    viewModel.onEvent(TrackerOverviewEvent.OnPreviousDayClick)

                },
                onNextDayClick = {
                    viewModel.onEvent(TrackerOverviewEvent.OnNextDayClick)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.spaceMedium)
            )
            //space before the items
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
        }
        //multiple items with the meal default types
        items(state.meals) { meal ->
            ExpandableMeal(
                meal = meal,
                onToggleClick = {
                    viewModel.onEvent(TrackerOverviewEvent.OnToggleMealClick(meal))
                },
                content = {
                    //here we will have a column because inside it we will have the add button as weel after all foods
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = spacing.spaceSmall)
                    ) {
                        //Here loop over the trackedfood we get from the state
                        //fr each food in the api
                        val foods = state.trackedFoods.filter {
                            it.mealType == meal.mealTYpe
                        }
                        foods.forEach { food ->
                            //if (food.mealType.name == meal.mealTYpe.name){
                                TrackedFoodItem(
                                    trackedFood = food,
                                    onDeleteClick = {
                                        viewModel.
                                        onEvent(
                                            TrackerOverviewEvent.OnDeleteTrackedFoodClick(food)
                                        )
                                    }
                                )
                                Spacer(modifier = Modifier.height(spacing.spaceMedium))
                            //}


                        }
                        AddButton(
                            text = stringResource(
                                id = R.string.add,
                                meal.name.asString(context)
                            ),
                            onClick = {

//                                viewModel.onEvent(TrackerOverviewEvent.OnAddFoodClick(meal))
                                      /**nAVIGATION IMPROVEMENT trigger callback*/
                                onNavigateToSearch(
                                    meal.name.asString(context).lowercase(),
                                    state.date.dayOfMonth,
                                    state.date.monthValue,
                                    state.date.year
                                )

                            },
                            modifier = Modifier.fillMaxWidth()
                        )


                    }

                },
                modifier = Modifier.fillMaxWidth()

            )

        }
    }

}