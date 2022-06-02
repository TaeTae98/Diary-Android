package com.taetae98.diary.feature.common.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

@Composable
inline fun <reified VM : ViewModel> NavController.navGraphViewModel(id: Int? = null): VM = hiltViewModel(
    getViewModelStoreOwner(
        id ?: currentDestination?.parent?.id
            ?: throw IllegalStateException("NavigationGraph Does Not Exist")
    )
)

fun NavController.setResult(key: String, result: Any?) {
    val previous = previousBackStackEntry ?: return
    previous.savedStateHandle[key] = result
}

fun NavController.removeResult(key: String) {
    val current = currentBackStackEntry ?: throw Exception("CurrentBackStackEntry is null")
    current.savedStateHandle.remove<Any?>(key)
}

@Composable
fun<T: Any> NavController.getResult(key: String): State<T?> {
    val current = currentBackStackEntry ?: throw Exception("CurrentBackStackEntry is null")
    return current.savedStateHandle.getLiveData<T>(key).observeAsState()
}
