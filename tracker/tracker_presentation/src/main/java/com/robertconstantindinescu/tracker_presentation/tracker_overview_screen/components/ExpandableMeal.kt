package com.robertconstantindinescu.tracker_presentation.tracker_overview_screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import com.robertconstantindinescu.tracker_presentation.R
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.robertconstantindinescu.core_ui.LocalSpacing
import com.robertconstantindinescu.tracker_presentation.shared_components.NutrientInfo
import com.robertconstantindinescu.tracker_presentation.shared_components.UnitDisplay
import com.robertconstantindinescu.tracker_presentation.tracker_overview_screen.Meal

@Composable
fun ExpandableMeal(
    meal: Meal, //this will come from state
    onToggleClick: () -> Unit,
    content: @Composable () -> Unit, //the contentn will be a composable itself
    modifier: Modifier

) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current


    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onToggleClick()
                }
                .padding(spacing.spaceMedium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = meal.drawableRes),
                contentDescription = meal.name.asString(context)
            )
            Spacer(modifier = Modifier.width(spacing.spaceMedium))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                //text and extended arrow
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = meal.name.asString(context),
                        style = MaterialTheme.typography.h3
                    )
                    Icon(
                        imageVector = if (meal.isExpanded) {
                            Icons.Default.KeyboardArrowUp
                        } else Icons.Default.KeyboardArrowDown,
                        contentDescription = if (meal.isExpanded) {
                            stringResource(id = R.string.collapse)
                        } else stringResource(id = R.string.extend)
                    )
                }
                Spacer(modifier = Modifier.height(spacing.spaceSmall))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    UnitDisplay(
                        amount = meal.calories, unit = stringResource(id = R.string.kcal),
                        amountTextSize = 30.sp
                    )

                    Row {
                        NutrientInfo(
                            name = stringResource(id = R.string.carbs),
                            amount = meal.carbs,
                            unit = stringResource(id = R.string.grams)
                        )
                        Spacer(modifier = Modifier.width(spacing.spaceSmall))
                        NutrientInfo(
                            name = stringResource(id = R.string.protein),
                            amount = meal.protein,
                            unit = stringResource(id = R.string.grams)
                        )
                        Spacer(modifier = Modifier.width(spacing.spaceSmall))
                        NutrientInfo(
                            name = stringResource(id = R.string.fat),
                            amount = meal.fat,
                            unit = stringResource(id = R.string.grams)
                        )
                    }

                }
            }
        }
        Spacer(modifier = Modifier.height(spacing.spaceMedium))

        AnimatedVisibility(visible = meal.isExpanded) {
            content()
        }

    }


}








































