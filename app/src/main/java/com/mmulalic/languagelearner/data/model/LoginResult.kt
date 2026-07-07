package com.mmulalic.languagelearner.data.model

sealed class LoginResult {
    object Success : LoginResult()

    sealed class Error : LoginResult() {
        object InvalidCredentials : Error()
        object NoInternet : Error()
        object ServerError : Error()
        object Unknown : Error()
    }
}