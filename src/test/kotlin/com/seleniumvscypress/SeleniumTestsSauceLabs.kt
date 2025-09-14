package com.seleniumvscypress

import com.seleniumvsplaywright.core.BaseSelenium
import com.seleniumvsplaywright.core.BrowserConfigSelenium
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
    fun quitAll() = driver.quit()

/*    @AfterEach
    @Attachment(type = "image/png")
    fun testTakeScreen() = takeScreen()*/

    @ParameterizedTest
    @CsvFileSource(resources = arrayOf("/links.csv"), numLinesToSkip = 1)
    fun round5_links(type: String, urlSite: String){
        step("Checks that social links open in a new tab - Selenium")
        val originalWindow = driver.windowHandle
        driver.switchTo().window(originalWindow)

        click(".social_${type} a")
        val newWindow = driver.windowHandles.last()
        driver.switchTo().window(newWindow)
        assertEquals(urlSite, driver.currentUrl)
    }
}
