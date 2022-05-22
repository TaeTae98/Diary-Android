package com.taetae98.diary.feature.compose.input

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import com.taetae98.diary.feature.compose.diary.DiaryTextField
import com.taetae98.diary.feature.resource.StringResource

@Composable
fun ClearTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String? = null,
    isError: Boolean = false,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE
) {
    val focusRequester = remember { FocusRequester() }

    DiaryTextField(
        modifier = modifier
            .focusRequester(focusRequester),
        value = value,
        onValueChange = onValueChange,
        label = if (label == null) {
            null
        } else {
            { Text(text = label) }
        },
        trailingIcon = if (value.isEmpty()) {
            null
        } else {
            {
                IconButton(
                    onClick = {
                        onValueChange("")
                        focusRequester.requestFocus()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Clear,
                        contentDescription = stringResource(id = StringResource.clear)
                    )
                }
            }
        },
        isError = isError,
        singleLine = singleLine,
        maxLines = maxLines,
    )
}