package com.seleniumvscypress

import com.seleniumvsplaywright.core.BasePlaywright
import com.seleniumvsplaywright.core.BrowserConfigPlaywright
import com.seleniumvsplaywright.model.FeatureLogin
import com.seleniumvsplaywright.model.UserData
import io.qameta.allure.Allure.step
import org.junit.jupiter.api.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import org.testng.Assert.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PlaywrightTestsSauceLabs: BasePlaywright(BrowserConfigPlaywright().setPWBrowser()) {

    private val login = FeatureLogin()
    private val userData = UserData()

    @BeforeEach
    fun setup(){
        navigate(login.sauceDemoUrl)
        doLogin(userData.username, userData.password)
    }

    @AfterAll
    fun quit() {
       pw.close()
    }

/*    @AfterEach
    @Attachment(type = "image/png")
    fun testTakeScreen() = takeScreen()*/

    @ParameterizedTest
    @CsvFileSource(resources = arrayOf("/links.csv"), numLinesToSkip = 1)
    fun round5_links(type: String, urlSite: String){
        step("Checks that social links open in a new tab  - Playwright")
        val newTab = pw.context().waitForPage {
            val newActivePage = pw.context().pages()[0]
            newActivePage.bringToFront()
            click(".social_${type} a", true)
        }
        assertEquals(newTab.url(), urlSite)
    }
}
