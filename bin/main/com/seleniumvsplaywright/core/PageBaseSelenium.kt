package com.seleniumvsplaywright.core
import com.seleniumvsplaywright.model.FeatureLogin
import com.seleniumvsplaywright.model.UserData
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

open class PageBaseSelenium(open val dv: WebDriver) {

    private val featureLogin = FeatureLogin()
    val user = UserData()

    fun doLogin(username: String, password: String) {
        dv.findElement(By.cssSelector(featureLogin.inputUsername)).sendKeys(username)
        dv.findElement(By.cssSelector(featureLogin.inputPassword)).sendKeys(password)
        dv.findElement(By.cssSelector(featureLogin.btnLogin)).click()
    }

}