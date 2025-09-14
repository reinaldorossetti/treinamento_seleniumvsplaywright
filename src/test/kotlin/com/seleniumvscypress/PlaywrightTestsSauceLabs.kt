package com.seleniumvscypress

import com.microsoft.playwright.FrameLocator
import com.microsoft.playwright.Page
import com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat
import com.seleniumvscypress.core.BasePlaywright
import com.seleniumvscypress.core.BrowserConfigPlaywright
import com.seleniumvscypress.core.model.DesafioElements
import com.seleniumvscypress.core.model.FeatureLogin
import com.seleniumvscypress.core.model.UserData
import io.qameta.allure.Allure.step
import io.qameta.allure.Attachment
import org.junit.jupiter.api.*
import org.junit.jupiter.api.parallel.ResourceLock
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import org.junit.jupiter.params.provider.ValueSource
import org.testng.Assert.assertEquals
import org.testng.annotations.AfterTest

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
