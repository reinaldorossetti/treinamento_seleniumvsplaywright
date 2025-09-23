package com.seleniumvsplaywright

import com.microsoft.playwright.FrameLocator
import com.microsoft.playwright.Page
import com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat
import com.seleniumvsplaywright.core.config.BrowserConfigPlaywright
import com.seleniumvsplaywright.core.libs.BasePlaywright
import com.seleniumvsplaywright.model.DesafioElements
import io.qameta.allure.Allure.step
import io.qameta.allure.Attachment
import org.junit.jupiter.api.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.testng.Assert.assertEquals
import org.testng.annotations.AfterTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PlaywrightTests: BasePlaywright(BrowserConfigPlaywright().setPWBrowser()) {

    private val elements = DesafioElements()

    @AfterTest
    fun quit() = closeBrowser()

// Deixei comentado pois o print degrada a performance dos testes
    @AfterEach
    @Attachment(type = "image/png")
    fun testTakeScreen() = takeScreen()

    @Test
    fun round1Login(){
        pw.navigate(baseURLDemoSite)
        step("Realizando teste de login - Playwright")
        pw.fill(elements.username,"angie")
        pw.fill(elements.password,"1234")
        takeScreen("Tela de login")
        pw.click(elements.loginButton)
        assertEquals(pw.locator(elements.headerText).textContent().trim(),
            "Financial Overview")
    }

    @ParameterizedTest
    @ValueSource(strings = ["Ginger", "Paprika", "Garlic", "Chili Powder"])
    fun round2_testSelect(input: String){
        navigate("/ingredients/select")
        step("Realizando teste do comboxbox de seleção - Playwright")
        assertEquals(input, selectOption(elements.selectSpices, input))
    }

    @Test
    fun round3FileUpload(){
        navigate("/ingredients/file-picker")
        step("Realizando o upload da foto - Playwright")
        setInputFiles(elements.fileUploadInput,"$path/files/cypress-soh-que-nao.PNG")
        assertThat(pw.getByText("Upload Preview")).isVisible()
    }

    @Test
    fun round4Iframe_CrossDomain(){
        navigate("/ingredients/iframe")
        step("Realizando a troca de iframe, ou seja pra tela do youtube para dar o clique  - Playwright")
        val iframeLocator: FrameLocator = pw.frameLocator(elements.iframeID)
        iframeLocator.locator(elements.youtubePlayButton).click()
    }

}
