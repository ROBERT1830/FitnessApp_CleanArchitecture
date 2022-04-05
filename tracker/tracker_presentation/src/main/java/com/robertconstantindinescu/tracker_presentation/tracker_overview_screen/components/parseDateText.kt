package com.robertconstantindinescu.tracker_presentation.tracker_overview_screen.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.robertconstantindinescu.tracker_presentation.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * This composable will restunr the string that we parse for that date
 */
@Composable
fun parseDateText(date: LocalDate): String{
    //get the today date
    val today = LocalDate.now()

    return when(date){
        today -> stringResource(id = R.string.today)
        today.minusDays(1) -> stringResource(id = R.string.yesterday)
        today.plusDays(1) -> stringResource(id = R.string.tomorrow)
        else -> DateTimeFormatter.ofPattern("dd LLLL").format(date)
    }
}