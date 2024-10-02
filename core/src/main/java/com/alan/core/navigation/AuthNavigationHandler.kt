package com.alan.core.navigation

interface AuthNavigationHandler {
    fun onOtpVerified()
    fun isUserLoggedIn(): Boolean
}