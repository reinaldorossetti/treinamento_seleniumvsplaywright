package com.seleniumvscypress

import com.microsoft.playwright.FrameLocator
import com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat
import com.seleniumvscypress.core.BasePlaywright
import com.seleniumvscypress.core.BrowserConfigPlaywright
import io.qameta.allure.Allure.step
import org.junit.jupiter.api.*
import org.junit.jupiter.api.parallel.ResourceLock
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.testng.Assert.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PlaywrightTests: BasePlaywright(BrowserConfigPlaywright().setPWBrowser()) {

    @AfterAll
    fun quit() {
       pw.close()
    }

/*    @AfterEach
    @Attachment(type = "image/png")
    fun testTakeScreen() = takeScreen()*/

    @Test @ResourceLock(value = "resources")
    fun round1Login(){
        pw.navigate("https://demo.applitools.com/")
        step("Realizando teste de login - Playwright")
        pw.fill("#username","angie")
        pw.fill("#password","1234")
        // takeScreen("Tela de login")
        pw.click("#log-in")
        assertEquals(pw.locator(".element-header:nth-of-type(2)").textContent().trim(),
            "Financial Overview")
    }

    @ParameterizedTest
    @ValueSource(strings = ["Ginger", "Paprika", "Garlic", "Chili Powder"])
    fun round2_testSelect(input: String){
        navigate("/ingredients/select")
        step("Realizando teste do comboxbox de seleção")
        Assertions.assertEquals(input, selectOption("#spices-select-single", input))
    }

    @Test
    fun round3FileUpload(){
        navigate("/ingredients/file-picker")
        step("Realizando o upload da foto")
        setInputFiles("#photo-upload","$path/files/cypress-soh-que-nao.PNG")
        assertThat(pw.getByText("Upload Preview")).isVisible()
    }

    @Test
    fun round4Iframe_CrossDomain(){
        navigate("https://kitchen.applitools.com/ingredients/iframe")
        step("Realizando a troca de iframe, ou seja pra tela do youtube para dar o clique.")
        val iframeLocator: FrameLocator = pw.frameLocator("#youtube-table-cypress");
        // Locate an element inside the iframe and interact with it
        val button = iframeLocator.locator("button[aria-label=Reproduzir]")
        button.click()
        assertThat(button).not().isVisible()
    }

    @Test
    fun round5_waitForFilter(){
        navigate("https://automationbookstore.dev/")
        step("Wait for Filter")
        pw.locator("#pid1_author")
    }
}
