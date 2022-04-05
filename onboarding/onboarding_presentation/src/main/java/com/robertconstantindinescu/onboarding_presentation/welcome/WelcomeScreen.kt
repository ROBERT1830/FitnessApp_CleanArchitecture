package com.robertconstantindinescu.onboarding_presentation.welcome

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.robertconstantindinescu.core_ui.LocalSpacing
import com.robertconstantindinescu.onboarding_presentation.R
import com.robertconstantindinescu.onboarding_presentation.components.ActionButton


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WelcomeScreenPreview(){
    WelcomeScreen()
}

@Composable
fun WelcomeScreen(


    onNextClick: () -> Unit = {}
) {

    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceMedium),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Text(
            text = stringResource(id = R.string.welcome_text),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h1
        )

        Spacer(modifier = Modifier.height(spacing.spaceMedium))

        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = {
                onNextClick()

            },
            modifier = Modifier.align(Alignment.CenterHorizontally)

        )


    }
}