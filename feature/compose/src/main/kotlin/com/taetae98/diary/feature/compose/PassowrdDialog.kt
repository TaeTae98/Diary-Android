package com.taetae98.diary.feature.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.taetae98.diary.feature.resource.StringResource

@Composable
fun PasswordDialog(
    password: String,
    onSuccess: () -> Unit,
    onFail: () -> Unit = {},
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties()
) {
    val focusRequester = remember { FocusRequester() }
    val (input, setInput) = remember { mutableStateOf("") }
    val (isError, setIsError) = remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        Card {
            Column {
                SecureInputCompose(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    value = input,
                    onValueChange = {
                        setIsError(false)
                        setInput(it)
                    },
                    label = stringResource(id = StringResource.password),
                    isError = isError
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 48.dp)
                        .padding(0.dp),
                    onClick = {
                        if (input == password) {
                            onSuccess()
                        } else {
                            setIsError(true)
                            onFail()
                        }
                    },
                    shape = RectangleShape
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Check,
                        contentDescription = stringResource(id = StringResource.ok)
                    )
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}