package com.taetae98.diary.feature.memo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taetae98.diary.domain.MemoEntity
import com.taetae98.diary.feature.theme.DiaryTheme

@Composable
fun MemoCompose(
    modifier: Modifier = Modifier,
    memoEntity: MemoEntity? = null
) {
    Card(
        modifier = modifier.heightIn(min = 60.dp),
    ) {
        if (memoEntity == null) {
            Loading()
        } else {
            Memo(memoEntity = memoEntity)
        }
    }
}

@Composable
private fun Loading(
    modifier: Modifier = Modifier
) {
    CircularProgressIndicator(
        modifier = modifier.wrapContentSize(),
        color = DiaryTheme.onSurfaceColor,
    )
}

@Composable
private fun Memo(
    modifier: Modifier = Modifier,
    memoEntity: MemoEntity
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = memoEntity.title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
        )
    }
}