package com.seleniumvsplaywright.core.libs
import com.microsoft.playwright.Page
import com.seleniumvsplaywright.model.FeatureLogin
import com.seleniumvsplaywright.model.UserData

open class PageBasePW() {

    open lateinit var pw: Page
    private val featureLogin = FeatureLogin()
    val user = UserData()

    fun doLogin(username: String, password: String) {
        pw.locator(featureLogin.inputUsername).fill(username)
        pw.locator(featureLogin.inputPassword).fill(password)
        pw.locator(featureLogin.btnLogin).click()
    }

}