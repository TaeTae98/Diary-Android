package com.taetae98.diary.feature.compose

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntOffset
import com.taetae98.diary.feature.common.isFalse

fun Modifier.draggable(
    orientation: Orientation,
    enabled: Boolean = true,
    onDragStarted: (Offset) -> Unit = {},
    onDrag: (Float) -> Unit = {},
    onDragStopped: (Float) -> Boolean = { false },
) = composed {
    var offset by remember { mutableStateOf(0F) }
    val draggableState = rememberDraggableState {
        offset += it
        onDrag(offset)
    }

    draggable(
        state = draggableState,
        orientation = orientation,
        enabled = enabled,
        onDragStarted = {
            onDragStarted(it)
        },
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
}