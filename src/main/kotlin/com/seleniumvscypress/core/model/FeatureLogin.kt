package com.seleniumvscypress.core.model

data class FeatureLogin(
    val inputUsername: String = "#user-name",
    val inputPassword: String = "#password",
    val btnLogin: String = "#login-button",
    val sauceDemoUrl: String = "https://www.saucedemo.com"
)
