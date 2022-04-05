package com.plcoding.calorytracker.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.plcoding.calorytrackerprep.ui.theme.Typography
import com.robertconstantindinescu.core_ui.*

private val DarkColorPalette = darkColors(
    primary = BrightGreen,
    primaryVariant = DarkGreen,
    secondary = Orange,
    background = MediumGray,
    onBackground = TextWhite,
    surface = LightGray,
    onSurface = TextWhite,
    onPrimary = Color.White,
    onSecondary = Color.White,
)

private val LightColorPalette = lightColors(
    primary = BrightGreen,
    primaryVariant = DarkGreen,
    secondary = Orange,
    background = Color.White,
    onBackground = DarkGray,
    surface = Color.White,
    onSurface = DarkGray,
    onPrimary = Color.White,
    onSecondary = Color.White,
)

@Composable
fun CaloryTrackerTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    //we can acces LocalSpacing because we included the core.ui module
    /**
     * Here we define the data that will be provided and therefore retirner by LocalSpacing
     * you can change one of those variables.
     * This will define the values that will be passes through the composition.
     *
     * pOR EJEMPLO ESTO lo puedes usar para pasar alunos valores compartidos ne
     * el arbol de composable a parte de como tema general
     * Un claro ejemplo esta en esta web
     *
     * https://medium.com/geekculture/jetpack-compose-compositionlocal-what-you-need-to-know-979a4aef6412
     */
    CompositionLocalProvider(LocalSpacing provides Dimensions()) {

        MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }


}