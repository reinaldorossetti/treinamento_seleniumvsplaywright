package com.seleniumvscypress.core

import com.microsoft.playwright.Locator
import com.microsoft.playwright.Page
import com.microsoft.playwright.options.LoadState
import io.qameta.allure.Allure
import java.io.ByteArrayInputStream
import java.io.File
import java.io.InputStream
import java.nio.file.Path

/**
 * Classe BaseCore contém funções globais básicas para as pws.
 * Funções específicas devem esta na pw.
 * Passar o elemento mapeado sempre para a Basepw.
 */
open class BasePlaywright(override var pw: Page): PageBasePW() {


    /**
     * A funcao loadpw espera a página carregar por completo.
    "LOAD" - wait for the load event to be fired.
    "DOMCONTENTLOADED" - wait for the DOMContentLoaded event to be fired.
    "NETWORKIDLE" - wait until there are no network connections for at least
     */

    fun loadpw() {
        pw.waitForLoadState(LoadState.DOMCONTENTLOADED)
    }

    // espera 5s somente na segunda tentativa.
    fun sleep(value: Long = 5) {
        val sleepValue = value * 1000
        Thread.sleep(sleepValue)
    }

    /**
     * click usando o setForce(true), se o elemento existir vai dar um click.
     * @property locator css.
     * @property retry true or false, default is false.
     */
    fun click(locator: String, focus: Boolean = true) {
        loadpw()
        if (!focus) {
            pw.click(locator)
        } else {
            pw.locator(locator).focus()
            pw.locator(locator).click(forcedClickOptions)
        }
    }

    /**
     * A função navigate
     * @param url passar a url que vai navegar.

     **/
    fun navigate(urlSite: String) {
        if (urlSite.contains("https")) {
            pw.navigate(urlSite)
        } else {
            pw.navigate(baseURL + urlSite)
        }
    }

    /**
     * A função selectOption seleciona um elemento no combobox.
     * @param selectOption passar o locator/css element a opção do comboboxs.
     **/
    fun selectOption(Locator: String, value: String): String? {
        pw.locator(Locator).selectOption(value)
        loadpw()
        return pw.locator("option:checked").textContent()
    }

    /**
     * A função selectOption seleciona um elemento no combobox.
     * @param selectOption passar o locator/css element a opção do comboboxs.
     **/
    fun setInputFiles(locator: String, value: String) {
        loadpw()
        pw.locator(locator).setInputFiles(Path.of(value));
    }

    /**
     * A função takeScreen tira a foto via selenium e anexo no reporte do allure.
     * @param screenName passar o nome da tela ou sem vai pegar o valor padrão.
     */
    fun takeScreen(screenName: String = "AfterTestScreen"){

        // Attaching a screenshot (assuming you have a byte array of the screenshot)
        val screenshotBytes = (pw).screenshot() // Example: getting a screenshot from Playwright's Page object
        Allure.addAttachment(screenName,
            screenshotBytes.inputStream())
    }

}

/*
fun main(){
    val driver = BrowserConfig().setChrome()
    val base = Basepw(driver)
    base.visit("/")
}*/
