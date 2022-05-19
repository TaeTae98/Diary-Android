package com.taetae98.diary.feature.compose

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import com.taetae98.diary.feature.common.isFalse

@Composable
fun DraggableCompose(
    orientation: Orientation,
    enabled: Boolean = true,
    onDrag: (Float) -> Unit = {},
    onDragStopped: (Float) -> Boolean = { false },
    content: @Composable (modifier: Modifier) -> Unit
) {
    var offset by remember { mutableStateOf(0F) }
    val state = rememberDraggableState {
        offset += it
        onDrag(offset)
    }

    content(
        modifier = Modifier
            .draggable(
                state = state,
                orientation = orientation,
                enabled = enabled,
                onDragStopped = {
                    if (onDragStopped(it).isFalse()) {
                        offset = 0F
                    }
                }
            ).offset {
                if (orientation == Orientation.Vertical) {
                    IntOffset(x = 0, y = offset.toInt())
                } else {
                    IntOffset(x = offset.toInt(), y = 0)
                }
            }
    )
}