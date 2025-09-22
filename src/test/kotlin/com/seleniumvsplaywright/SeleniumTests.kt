package com.seleniumvsplaywright

import com.seleniumvsplaywright.core.libs.BaseSelenium
import com.seleniumvsplaywright.core.config.BrowserConfigSelenium
import com.seleniumvsplaywright.model.DesafioElements
import io.qameta.allure.Allure.step
import org.junit.jupiter.api.*
import org.junit.jupiter.api.parallel.ResourceLock
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SeleniumTests: BaseSelenium(BrowserConfigSelenium().setChrome()) {

    private val elements = DesafioElements()

    @AfterAll
    fun quit() = closeBrowser()

    /*
    // Deixei comentado pois o print degrada a performance dos testes
        @AfterEach
        @Attachment(type = "image/png")
        fun testTakeScreen() = takeScreen()
    */

    @Test @ResourceLock(value = "resources")
    fun round1Login(){
        visit("https://demo.applitools.com/")
        step("Realizando teste de login - Selenium")
        find(elements.username).sendKeys(elements.usernameValue)
        find(elements.password).sendKeys(elements.passwordValue)
        takeScreen("Tela de login")
        click(elements.loginButton)
        find(elements.headerText).contains("Financial Overview")
    }

    @ParameterizedTest
    @ValueSource(strings = ["ginger", "paprika", "garlic", "chili-powder"])
    fun round2_testSelect(input: String){
        println(input)
        visit("/ingredients/select")
        step("Realizando teste do comboxbox de seleção - Selenium")
        find("#spices-select-single").selectByValue(input)
    }

    @Test
    fun round3FileUpload(){
        visit("/ingredients/file-picker")
        step("Realizando o upload da foto - Selenium")
        find("#photo-upload", true).sendKeys("$path/files/cypress-soh-que-nao.PNG")
    }

    @Test
    fun round4Iframe_CrossDomain(){
        visit("/ingredients/iframe")
        step("Realizando a troca de iframe, ou seja pra tela do youtube para dar o clique  - Selenium")
        frameIndex(1)
        find("button[aria-label=Reproduzir]", focus = true).click()
    }
}
