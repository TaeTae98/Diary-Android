package com.taetae98.diary.feature.memo.screen

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.taetae98.diary.feature.memo.viewmodel.MemoFileViewModel

@Composable
fun MemoFileSelectScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    memoFileViewModel: MemoFileViewModel = hiltViewModel()
) {
//    FileSelectCompose(
//        modifier = modifier,
//        title = memoFileViewModel.folder.collectAsState().value?.title,
//        OnNavigateUp = { navController.navigateUp() },
//        isRoot = memoFileViewModel.folderId.collectAsState().value == null,
//        onBack = { memoFileViewModel.navigateUp() },
//        folder = memoFileViewModel.folderPaging.collectAsLazyPagingItems(),
//        file = memoFileViewModel.filePaging.collectAsLazyPagingItems()
//    )

    BackHandler(
        enabled = memoFileViewModel.folderId.collectAsState().value != null
    ) {
        memoFileViewModel.navigateUp()
    }
}