package com.seleniumvsplaywright

import com.seleniumvsplaywright.core.libs.BaseSelenium
import com.seleniumvsplaywright.core.config.BrowserConfigSelenium
import com.seleniumvsplaywright.model.FeatureLogin
import com.seleniumvsplaywright.model.UserData
import io.qameta.allure.Allure.step
import org.junit.jupiter.api.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import org.testng.Assert.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SeleniumTestsSauceLabs: BaseSelenium(BrowserConfigSelenium().setChrome()) {

    private val login = FeatureLogin()
    private val userData = UserData()

    @BeforeEach
    fun setup(){
        visit(login.sauceDemoUrl)
        doLogin(userData.username, userData.password)
    }

    @AfterAll
    fun quitAll() = closeBrowser()

    /*
    // Deixei comentado pois o print degrada a performance dos testes
        @AfterEach
        @Attachment(type = "image/png")
        fun testTakeScreen() = takeScreen()
    */

    @ParameterizedTest
    @CsvFileSource(resources = arrayOf("/links.csv"), numLinesToSkip = 1)
    fun round5_links(type: String, urlSite: String){
        step("Checks that social links open in a new tab - Selenium")
        val originalWindow = dv.windowHandle
        dv.switchTo().window(originalWindow)

        click(".social_${type} a")
        val newWindow = dv.windowHandles.last()
        dv.switchTo().window(newWindow)
        assertEquals(dv.currentUrl, urlSite)
    }
}
