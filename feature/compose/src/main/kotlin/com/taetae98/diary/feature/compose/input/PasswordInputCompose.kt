package com.taetae98.diary.feature.compose.input

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.VpnKey
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.taetae98.diary.feature.common.isFalse
import com.taetae98.diary.feature.resource.StringResource
import com.taetae98.diary.feature.theme.DiaryTheme

@Composable
fun PasswordInputCompose(
    modifier: Modifier = Modifier,
    hasPassword: Boolean,
    onHasPasswordChanged: (Boolean) -> Unit,
    password: String,
    onPasswordChanged: (String) -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        PasswordHeader(
            hasPassword = hasPassword,
            onHasPasswordChanged = onHasPasswordChanged
        )
        PasswordInput(
            modifier = Modifier
                .fillMaxWidth(),
            hasPassword = hasPassword,
            password = TextFieldValue(
                text = password,
                selection = TextRange(password.length)
            ),
            onPasswordChanged = {
                onPasswordChanged(it.text)
            }
        )
    }
}

@Composable
fun PasswordInputCompose(
    modifier: Modifier = Modifier,
    hasPassword: Boolean,
    onHasPasswordChanged: (Boolean) -> Unit,
    password: TextFieldValue,
    onPasswordChanged: (TextFieldValue) -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        PasswordHeader(
            hasPassword = hasPassword,
            onHasPasswordChanged = onHasPasswordChanged
        )
        PasswordInput(
            modifier = Modifier
                .fillMaxWidth(),
            hasPassword = hasPassword,
            password = password,
            onPasswordChanged = onPasswordChanged
        )
    }
}

@Composable
private fun PasswordHeader(
    modifier: Modifier = Modifier,
    hasPassword: Boolean,
    onHasPasswordChanged: (Boolean) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1F)
                .padding(8.dp),
            text = stringResource(id = StringResource.password)
        )

        IconButton(
            onClick = {
                onHasPasswordChanged(hasPassword.not())
            }
        ) {
            if (hasPassword) {
                Icon(
                    tint = DiaryTheme.primaryColor,
                    imageVector = Icons.Rounded.VpnKey,
                    contentDescription = stringResource(id = StringResource.password)
                )
            } else {
                Icon(
                    imageVector = Icons.Rounded.VpnKey,
                    contentDescription = stringResource(id = StringResource.password)
                )
            }
        }
    }
}

@Composable
private fun PasswordInput(
    modifier: Modifier = Modifier,
    hasPassword: Boolean,
    password: TextFieldValue,
    onPasswordChanged: (TextFieldValue) -> Unit
) {
    if (hasPassword.isFalse()) {
        return
    }

    val focusRequester = remember { FocusRequester() }

    SecureTextField(
        modifier = modifier
            .focusRequester(focusRequester),
        value = password,
        onValueChange = onPasswordChanged
    )

    LaunchedEffect(hasPassword) {
        onPasswordChanged(
            TextFieldValue(
                text = password.text,
                selection = TextRange(password.text.length)
            )
        )

        focusRequester.requestFocus()
    }
}