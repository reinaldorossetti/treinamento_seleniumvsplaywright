package com.seleniumvscypress

import com.seleniumvscypress.core.BasePlaywright
import com.seleniumvscypress.core.BrowserConfigPlaywright
import io.qameta.allure.Allure.step
import io.qameta.allure.Attachment
import org.junit.jupiter.api.*
import org.junit.jupiter.api.parallel.ResourceLock
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.testng.Assert.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PlaywrightTests: BasePlaywright(BrowserConfigPlaywright().setPWBrowser()) {

    @AfterAll
    fun quit() {
       page.close()
    }

/*    @AfterEach
    @Attachment(type = "image/png")
    fun testTakeScreen() = takeScreen()*/

    @Test @ResourceLock(value = "resources")
    fun round1Login(){
        page.navigate("https://demo.applitools.com/")
        step("Realizando teste de login")
        page.fill("#username","angie")
        page.fill("#password","1234")
        // takeScreen("Tela de login")
        click("#log-in")
        assertEquals(page.locator(".element-header").first().textContent().trim(),
            "Financial Overview")
    }
/*
    @ParameterizedTest
    @ValueSource(strings = ["ginger", "paprika", "garlic", "chili-powder"])
    fun round2_testSelect(input: String){
        println(input)
        visit("/ingredients/select")
        step("Realizando teste do comboxbox de seleção")
        find("#spices-select-single").selectByValue(input)
    }

    @Test
    fun round3FileUpload(){
        visit("/ingredients/file-picker")
        step("Realizando o upload da foto")
        find("#photo-upload", true).sendKeys("$path/files/cypress-soh-que-nao.PNG")
    }

    @Test
    fun round4Iframe_CrossDomain(){
        visit("https://kitchen.applitools.com/ingredients/iframe")
        step("Realizando a troca de iframe, ou seja pra tela do youtube para dar o clique.")
        frameIndex(1)
        find("button[aria-label=Reproduzir]", focus = true).click()
    }

    @Test
    fun round5_waitForFilter(){
        visit("https://automationbookstore.dev/")
        step("Wait for Filter")
        find("#pid1_author", durationMax = 5)
    }*/
}
