package com.taetae98.diary.feature.compose.input

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import com.taetae98.diary.feature.compose.diary.DiaryTextField
import com.taetae98.diary.feature.resource.StringResource

@Composable
fun SecureTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String? = null,
    isError: Boolean = false,
    singleLine: Boolean = true,
    maxLine: Int = 1,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
) {
    SecureTextField(
        modifier = modifier,
        value = TextFieldValue(
            text = value,
            selection = TextRange(value.length)
        ),
        onValueChange = { onValueChange(it.text) },
        label = label,
        isError = isError,
        singleLine = singleLine,
        maxLine = maxLine,
        keyboardOptions = keyboardOptions
    )
}

@Composable
fun SecureTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    label: String? = null,
    isError: Boolean = false,
    singleLine: Boolean = true,
    maxLine: Int = 1,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
) {
    val (isVisible, setIsVisible) = remember { mutableStateOf(false) }

    DiaryTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = if (label == null) {
            null
        } else {
            {
                Text(text = label)
            }
        },
        trailingIcon = {
            IconButton(onClick = { setIsVisible(isVisible.not()) }) {
                Icon(
                    imageVector = if (isVisible) Icons.Rounded.Visibility else Icons.Rounded.VisibilityOff,
                    contentDescription = stringResource(id = if (isVisible) StringResource.show else StringResource.hide)
                )
            }
        },
        isError = isError,
        singleLine = singleLine,
        maxLines = maxLine,
        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = keyboardOptions
    )
}