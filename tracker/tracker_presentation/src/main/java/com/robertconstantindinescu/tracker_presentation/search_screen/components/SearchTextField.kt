package com.robertconstantindinescu.tracker_presentation.search_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.robertconstantindinescu.core_ui.LocalSpacing
import com.robertconstantindinescu.tracker_presentation.R

@Composable
fun SearchTextField(
    text: String,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier,
    hint: String = stringResource(id = R.string.search),
    shouldShowHint: Boolean = false,
    onFocusChanged: (FocusState) -> Unit
) {
    val spacing = LocalSpacing.current
    Box(modifier = modifier) {
        //basicTextField is for those cases in which we want to custom it
        BasicTextField(
            value = text,
            onValueChange = {
                onValueChange(it)
            },
            singleLine = true,
            keyboardActions = KeyboardActions( //this is used to perform the search action from the keyboard
                //when clcik on search button from keyboard
                onSearch = {
                    onSearch()
                    //by doing that we just make sure that we execute the default behaviour for clicking the search button on the keyboard which in this case closes the keyboard
                    defaultKeyboardAction(ImeAction.Search)
                }

            ),
            //this is to see the keyboard options specific to search
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search,

            ),
            modifier = Modifier
                .clip(RoundedCornerShape(5.dp))
                .padding(2.dp)//to make the shadow visible
                .shadow(
                    elevation = 2.dp,
                    shape = RoundedCornerShape(5.dp)
                )
                .background(MaterialTheme.colors.surface)
                .fillMaxWidth()
                .padding(spacing.spaceMedium)
                .padding(end = spacing.spaceMedium) //this is to ont overlap the icon
                .onFocusChanged {
                    onFocusChanged(it)
                }

        )
        if (shouldShowHint) {
            Text(
                text = hint,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Light,
                color = Color.Gray,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = spacing.spaceMedium)

            )
        }

        IconButton(
            onClick = onSearch,
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(id = R.string.search)
            )

        }
    }

}