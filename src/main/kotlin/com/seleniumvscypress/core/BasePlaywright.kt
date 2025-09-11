package com.seleniumvscypress.core

import com.microsoft.playwright.Page
import com.microsoft.playwright.options.LoadState
import com.microsoft.playwright.options.WaitForSelectorState
import io.qameta.allure.Allure
import java.io.ByteArrayInputStream
import java.io.File
import java.io.InputStream
import java.lang.Thread.sleep
import java.time.Duration
import kotlin.test.fail


/**
 * Classe BaseCore contém funções globais básicas para as pages.
 * Funções específicas devem esta na Page.
 * Passar o elemento mapeado sempre para a BasePage.
 */
open class BasePlaywright(var page: Page) {

    private val baseURL = "https://kitchen.applitools.com"
    private val timeout = 30L
    val path: String = System.getProperty("user.dir")
    private val inputStream: InputStream = File("$path\\src\\main\\kotlin\\com\\seleniumvscypress\\core\\jquery\\jquery-3.6.0.js").inputStream()
    private val inputString = inputStream.bufferedReader().use { it.readText() }
    // opcao que somente verifica se o elemento esta presente na DOM.
    val stateATTACHED: Page.WaitForSelectorOptions = Page.WaitForSelectorOptions().setState(WaitForSelectorState.ATTACHED)

    // opcao que somente verifica se o elemento esta visivel na DOM.
    val stateVISIBLE: Page.WaitForSelectorOptions = Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE)

    // opcao que somente verifica se o elemento Não esta presente na DOM.
    val stateDETACHED: Page.WaitForSelectorOptions = Page.WaitForSelectorOptions().setState(WaitForSelectorState.DETACHED)
    /*
    Usando o page factory do Appium client, pra definir tempo dos elementos.
     */

    /**
     * A funcao loadPage espera a página carregar por completo.
    "LOAD" - wait for the load event to be fired.
    "DOMCONTENTLOADED" - wait for the DOMContentLoaded event to be fired.
    "NETWORKIDLE" - wait until there are no network connections for at least
     */
    fun loadPage(){
        page.waitForLoadState(LoadState.LOAD)
        page.waitForLoadState(LoadState.DOMCONTENTLOADED)
        page.waitForLoadState(LoadState.NETWORKIDLE)
    }

    // espera 5s somente na segunda tentativa.
    fun sleep(value: Long = 5){
        val sleepValue = value * 1000
        Thread.sleep(sleepValue)
    }

    /**
     * click usando o setForce(true), se o elemento existir vai dar um click.
     * @property webElemento css or xpath.
     * @property retry true or false, default is false.
     */
    fun click(webElemento: String, retry: Boolean = true){
        try {
            page.waitForSelector(webElemento, stateATTACHED)
            page.click(webElemento, Page.ClickOptions().setForce(true))
        } catch (ex: Exception){
            println(ex.message)
            sleep()
            page.focus(webElemento)
            if (retry) page.click(webElemento, Page.ClickOptions().setForce(true)) else fail(ex.message)
        }
        loadPage()
    }

/*
    *//**
     * A função contains verifica se o elemento contém o texto esperado.
     * @param textElement passar o texto que vai validar no elemento.
     *//*
    fun WebElement.contains(textElement: String) {
        when (this.text.contains(textElement)) {
            true -> println("Elemento encontrado na página.")
            else -> fail("Assert Falhou -> ${this.text}")
        }
    }


    *//**
     * A função trigger faz o trigger quando o elemento espera um determinado evento.
     * @param cssSelector passar o css selector para a funcao.
     * @param comando passar o evento que deseja fazer o trigger.
     *//*
    fun trigger(cssSelector: String, comando: String, timeout: Long = 2) {
        (driver as JavascriptExecutor).executeScript(inputString)
        sleep(timeout * 1000) // tempo realizar a leitura do arquivo.
        (driver as JavascriptExecutor).executeScript("return $('$cssSelector').trigger(\"$comando\")")
    }

    *//**
     * A função type realiza o envio do texto para o elemento.
     * @param text passar o texto que vai validar no elemento.
     *//*
    fun WebElement.type(text: String) {
        sendKeys(this, text)
    }

    *//**
     * A função type realiza o envio do texto para o elemento.
     * @param text passar o texto que vai validar no elemento.
     *//*
    fun WebElement.selectByText(text: String) {
        Select(this).selectByVisibleText(text)
        if (this.findElement(By.cssSelector("option:checked")).
            text==text) {
            println("Elemento selecionado com sucesso")
        } else {
            fail("Teste falhou ao selecionar o elemento")
        }
    }

    *//**
     * A função selectByValue a seleção do combo por value.
     * @param value passar o texto que vai validar no elemento.
     *//*
    fun WebElement.selectByValue(value: String) {
        Select(this).selectByValue(value)
        if (this.findElement(By.cssSelector("option:checked")).
            getAttribute("value")==value) {
            println("Elemento selecionado com sucesso")
        } else {
            fail("Teste falhou ao selecionar o elemento")
        }
    }

    *//**
     * A função clickJS realiza o click via javascript.
     * @param element passa o elemento mapeado no factory.
     *//*
    fun clickJavaScript(element: WebElement) {
        (driver as JavascriptExecutor).executeScript("arguments[0].click();", element)
    }

    *//**
     * A função scrollIntoView realiza o click via javascript.
     * @param element passa o elemento mapeado no factory.
     *//*
    fun scrollIntoView(cssSelector: String, timeout: Long = 2) {
        (driver as JavascriptExecutor).executeScript(
            "document.querySelector('$cssSelector').scrollIntoView();")
        sleep(timeout * 1000) // tempo realizar o scroll.
    }

    *//**
     * A função selectByVisibleText faz a seleção do combo passando o elemento mapeado e o texto.
     * @param element passar o elemento mapeado.
     * @param text passar o texto visível que deseja selecionar.
     *//*
    fun selectByVisibleText(element: WebElement, text: String) {
        clickJavaScript(element)
        Select(element).selectByVisibleText(text)
    }

    *//**
     * A função takeScreen tira a foto via selenium e anexo no reporte do allure.
     * @param screenName passar o nome da tela ou sem vai pegar o valor padrão.
     *//*
    fun takeScreen(screenName: String = "TestScreen"){
        Allure.addAttachment(
            screenName, ByteArrayInputStream((driver as TakesScreenshot).getScreenshotAs(OutputType.BYTES))
        )
    }

    *//**
     * A função alert espera o alerta esta presente e passa o foco para o mesmo.
     *//*
    fun alert(): Alert {
        wait.until(ExpectedConditions.alertIsPresent())
        return driver.switchTo().alert()
    }

    *//**
     * A função frameId espera o Iframe esta presente e passa o foco para o mesmo.
     *//*
    fun frameIndex(frameid: Int) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameid))
    }*/

}

/*
fun main(){
    val driver = BrowserConfig().setChrome()
    val base = BasePage(driver)
    base.visit("/")
}*/
