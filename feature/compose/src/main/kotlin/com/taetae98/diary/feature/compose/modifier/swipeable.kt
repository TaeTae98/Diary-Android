package com.taetae98.diary.feature.compose.modifier

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.offset
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.ThresholdConfig
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.toSize
import com.taetae98.diary.feature.common.Const
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
fun <T : Any> Modifier.swipeable(
    initialValue: T,
    anchors: Map<Float, T>,
    orientation: Orientation = Orientation.Horizontal,
    enabled: Boolean = true,
    thresholds: (from: T, to: T) -> ThresholdConfig = { _, _ -> FractionalThreshold(Const.SWIPE_FRACTION_THRESHOLD) },
    onSwipe: (currentValue: T, targetValue: T) -> Unit = { _, _ -> }
) = composed {
    val swipeableState = rememberSwipeableState(initialValue = initialValue)
    onSwipe(swipeableState.currentValue, swipeableState.targetValue)

    swipeable(
        state = swipeableState,
        anchors = anchors,
        orientation = orientation,
        enabled = enabled,
        thresholds = thresholds
    ).offset {
        if (orientation == Orientation.Horizontal) {
            IntOffset(
                x = swipeableState.offset.value.roundToInt(),
                y = 0
            )
        } else {
            IntOffset(
                x = 0,
                y = swipeableState.offset.value.roundToInt()
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
fun Modifier.swipeable(
    initialValue: Int = 0,
    orientation: Orientation = Orientation.Horizontal,
    enabled: Boolean = true,
    thresholds: (from: Int, to: Int) -> ThresholdConfig = { _, _ -> FractionalThreshold(Const.SWIPE_FRACTION_THRESHOLD) },
    onSwipe: (currentValue: Int, targetValue: Int) -> Unit = { _, _ -> }
) = composed {
    val (size, setSize) = remember { mutableStateOf(Size.Zero) }

    onGloballyPositioned { layoutCoordinates ->
        setSize(layoutCoordinates.size.toSize())
    }.swipeable(
        initialValue = initialValue,
        anchors = mutableMapOf(
            0F to 0
        ).apply {
            if (orientation == Orientation.Horizontal && size.width != 0F) {
                put(size.width, 1)
                put(-size.width, -1)
            } else if (orientation == Orientation.Vertical && size.height != 0F) {
                put(size.height, 1)
                put(-size.height, -1)
            }
        },
        orientation = orientation,
        enabled = enabled,
        thresholds = thresholds,
        onSwipe = onSwipe
    )
}