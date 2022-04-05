package com.robertconstantindinescu.core_ui

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    /**
     * The thing here is that we cant import Dp because we dont have acces to compose
     * If we take a look in the gradle file of core, we will se that we inherit
     * from base module and this one does not include compose dependencie
     *
     * We can thought to put the dependencies of compose in that module but this will come
     * with some issues. If we do that it will work but also we will include
     * the compose dependency in mdules that dont need them like onbording tracker domain
     * All those models dont need presentation features.
     *
     * So this will be fixed by creating an other module "core Ui module"
     * which will contain only the ui specific dependencies that are shared.
     *
     * Here define al the ui code taht we want to share between multiple modules
     *
     * if we want to provide different dimensions for different screen just create an other instance
     * of this data class.
     */
    val default: Dp = 0.dp,
    val spaceExtraSmall: Dp = 4.dp,
    val spaceSmall: Dp = 8.dp,
    val spaceMedium: Dp = 16.dp,
    val spaceLarge: Dp = 32.dp,
    val spaceExtraLarge:Dp = 64.dp


)

/**
 * compositionLocalOf create a key tgat is used to pass specific values down the composable tree without
 * need to pass as a parameter those values. Is like local context.
 *
 * Es como que incluimos en el contexto de la app, esta data class con los valores indicados.
 *
 *
 * Here we create a compositionLocaloF A DATA CLASS. So that when we call our variable in the
 * composable, like val spacing = LocalSpacing.current, in that way compose will provide the
 * current value of Dimensions(). And then you can acces all of them by callin spacing.space.Small.
 * because will return Dimensions
 */
val LocalSpacing = compositionLocalOf { Dimensions() }