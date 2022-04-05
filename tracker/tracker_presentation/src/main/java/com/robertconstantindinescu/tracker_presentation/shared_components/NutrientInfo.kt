package com.robertconstantindinescu.tracker_presentation.shared_components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.robertconstantindinescu.core_ui.LocalSpacing

/**
 * This composable is a shared one because it will be used in the search screen as well
 */
@Composable
fun NutrientInfo(
    name: String, //pritein carbs ....
    amount: Int,
    unit: String,
    modifier: Modifier = Modifier,
    amountTextSize: TextUnit = 20.sp,
    amountColor: Color = MaterialTheme.colors.onBackground,
    unitTextSize: TextUnit = 14.sp,
    unitColor: Color = MaterialTheme.colors.onBackground,
    nameTextStyle: TextStyle = MaterialTheme.typography.body1 //name of the nutrient
){

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        UnitDisplay(amount = amount, unit = unit,
        amountTextSize = amountTextSize,
        amountColor = amountColor,
        unitTextSize = unitTextSize,
        unitColor = unitColor
        )

        Text(text = name,
        color = MaterialTheme.colors.onBackground,
        style = nameTextStyle,
        fontWeight = FontWeight.Light)
    }

}