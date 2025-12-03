package com.android.example.mymusicplaylist.util

sealed class UiEvent {
    data object PopBackstack : UiEvent()
    data class Navigate(val route: String) : UiEvent()
    data class ShowSnackBar(val message: String, val action: String? = null) : UiEvent()
}