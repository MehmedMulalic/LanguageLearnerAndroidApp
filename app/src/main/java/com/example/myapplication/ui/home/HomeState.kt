package com.example.myapplication.ui.home

sealed class HomeState {
    object Loading: HomeState()
    data class Error(val message: String): HomeState()
}