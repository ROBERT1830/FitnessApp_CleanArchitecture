package com.plcoding.calorytracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.plcoding.calorytracker.ui.theme.CaloryTrackerTheme
import com.robertconstantindinescu.core.domain.preferences.Preferences
import com.plcoding.calorytracker.navigation.Route
import com.robertconstantindinescu.onboarding_presentation.activity.ActivityScreen
import com.robertconstantindinescu.onboarding_presentation.age.AgeScreen
import com.robertconstantindinescu.onboarding_presentation.gender.GenderScreen
import com.robertconstantindinescu.onboarding_presentation.goal.GoalScreen
import com.robertconstantindinescu.onboarding_presentation.height.HeightScreen
import com.robertconstantindinescu.onboarding_presentation.nutrient_goal.NutrientGoalScreen
import com.robertconstantindinescu.onboarding_presentation.weight.WeightScreen
import com.robertconstantindinescu.onboarding_presentation.welcome.WelcomeScreen
import com.robertconstantindinescu.tracker_presentation.search_screen.SearchScreen
import com.robertconstantindinescu.tracker_presentation.tracker_overview_screen.TrackerOverviewScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val shouldShowOnBoarding = preferences.loadShouldShowOnBoarding()

        setContent {
            CaloryTrackerTheme {
                val navController = rememberNavController()
                //define the scaffold
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState
                ) {

                    NavHost(
                        navController = navController, startDestination = if (shouldShowOnBoarding){
                            Route.WELCOME
                        }else Route.TRACKER_OVERVIEW

                    ) {
                        composable(Route.WELCOME) {

                            WelcomeScreen(onNextClick = {
                                navController.navigate(Route.GENDER)
                            })
                        }
                        composable(Route.AGE) {

                            AgeScreen(
                                onNextClick ={
                                    navController.navigate(Route.HEIGHT)
                                } ,
                                scaffoldState = scaffoldState
                            )

                        }
                        composable(Route.GENDER) {
                            GenderScreen(onNavigate ={
                                navController.navigate(Route.AGE)
                            })

                        }
                        composable(Route.HEIGHT) {
                            HeightScreen(scaffoldState = scaffoldState, onNextClick = {

                                    navController.navigate(Route.WEIGHT)
                            })

                        }
                        composable(Route.WEIGHT) {
                            WeightScreen(
                                scaffoldState = scaffoldState,
                                onNextClick = {
                                    navController.navigate(Route.ACTIVITY)
                                }
                            )

                        }
                        composable(Route.NUTRIENT_GOAL) {


                            NutrientGoalScreen(
                                scaffoldState = scaffoldState,
                                onNextClick = {
                                    navController.navigate(Route.TRACKER_OVERVIEW)
                                }
                            )

                        }
                        composable(Route.ACTIVITY) {

                            ActivityScreen(onNextClick = {

                                    navController.navigate(Route.GOAL)

                            })

                        }
                        composable(Route.GOAL) {

                            GoalScreen(onNextClick = {
                                navController.navigate(Route.NUTRIENT_GOAL)
                            })
                        }
                        composable(Route.TRACKER_OVERVIEW) {


                            TrackerOverviewScreen(onNavigateToSearch = {mealName, day, month, year ->
                                navController.navigate(
                                    Route.SEARCH + "/$mealName" +
                                                            "/$day"+
                                                            "/$month"+
                                                            "/$year"
                                )
                            })
                        }
                        composable(
                            route = Route.SEARCH + "/{mealName}/{dayOfMonth}/{month}/{year}",
                            arguments = listOf(
                                navArgument("mealName") {
                                    type = NavType.StringType
                                },
                                navArgument("dayOfMonth") {
                                    type = NavType.IntType
                                },
                                navArgument("month") {
                                    type = NavType.IntType
                                },
                                navArgument("year") {
                                    type = NavType.IntType
                                },

                                )

                        ) {


                            val mealName = it.arguments?.getString("mealName")!!
                            val dayOfMonth = it.arguments?.getInt("dayOfMonth")!!
                            val month = it.arguments?.getInt("month")!!
                            val year = it.arguments?.getInt("year")!!

                            SearchScreen(
                                scaffoldState = scaffoldState,
                                mealName = mealName,
                                dayOfMonth = dayOfMonth,
                                month = month,
                                year = year,
                                onNavigateUp = {
                                    navController.navigateUp()
                                })
                        }
                    }
                }
            }
        }
    }
}