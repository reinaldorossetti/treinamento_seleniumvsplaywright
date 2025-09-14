package com.seleniumvsplaywright.core
import com.seleniumvsplaywright.model.FeatureLogin
import com.seleniumvsplaywright.model.UserData
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

open class PageBaseSelenium() {

    open lateinit var driver: WebDriver
    private val featureLogin = FeatureLogin()
    val user = UserData()

    fun doLogin(username: String, password: String) {
        driver.findElement(By.cssSelector(featureLogin.inputUsername)).sendKeys(username)
        driver.findElement(By.cssSelector(featureLogin.inputPassword)).sendKeys(password)
        driver.findElement(By.cssSelector(featureLogin.btnLogin)).click()
    }

}