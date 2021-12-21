package com.seleniumvscypress

import com.applitools.eyes.selenium.Eyes
import com.seleniumvscypress.core.Base
import com.seleniumvscypress.core.BrowserConfig
import com.seleniumvscypress.core.SetupEyes
import io.qameta.allure.Allure.step
import io.qameta.allure.Attachment
import org.junit.jupiter.api.*
import org.openqa.selenium.By
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SeleniumVsCypress: Base(BrowserConfig().setChrome()) {

    @AfterAll
    fun quit() = driver.quit()

    @AfterEach
    @Attachment(type = "image/png")
    fun testTakeScreen() { takeScreen() }

    @Test
    fun round1Login(){
        visit("https://demo.applitools.com/")
        step("Realizando teste de login")
        find("#username").sendKeys("angie")
        find("#password").sendKeys("1234")
        takeScreen("Tela de login")
        click("#log-in")
        find(".element-header").contains("Financial Overview")
    }

    @Test
    fun round2_testSelect(){
        visit("/ingredients/select")
        step("Realizando teste do comboxbox de seleção")
        find("#spices-select-single").selectByValue("ginger")
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
        driver.switchTo().frame(1)
        find("button[aria-label=Reproduzir]").click()
    }

    @Test
    fun round5_waitForFilter(){
        visit("https://automationbookstore.dev/")
        step("Wait for Filter")
        find("#pid1_author", durationMax = 5)
    }
}