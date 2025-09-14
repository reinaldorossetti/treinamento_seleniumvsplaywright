package com.seleniumvscypress.core
import com.microsoft.playwright.Locator
import com.microsoft.playwright.Page
import com.seleniumvscypress.core.model.FeatureLogin
import com.seleniumvscypress.core.model.UserData
import java.io.File
import java.io.InputStream

open class PageBasePW() {

    open lateinit var pw: Page
    private val FLogin = FeatureLogin()
    val user = UserData()

    val baseURL = "https://kitchen.applitools.com"
    val baseURLDemoSite = "https://demo.applitools.com/"

    private val timeout = 30L
    val path: String = System.getProperty("user.dir")
    private val inputStream: InputStream =
        File("$path\\src\\main\\kotlin\\com\\seleniumvscypress\\core\\jquery\\jquery-3.6.0.js").inputStream()
    private val inputString = inputStream.bufferedReader().use { it.readText() }
    var forcedClickOptions: Locator.ClickOptions = Locator.ClickOptions().setForce(true)


    fun doLogin(username: String, password: String) {
        pw.locator(FLogin.inputUsername).fill(username)
        pw.locator(FLogin.inputPassword).fill(password)
        pw.locator(FLogin.btnLogin).click()
    }

}