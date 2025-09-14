package com.seleniumvsplaywright.core

import com.seleniumvsplaywright.model.UserData
import io.appium.java_client.pagefactory.AppiumFieldDecorator
import io.qameta.allure.Allure
import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait
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
open class BaseSelenium(final override var dv: WebDriver) : PageBaseSelenium(dv) {

    private val baseURL = "https://kitchen.applitools.com"
    val path: String = System.getProperty("user.dir")
    val timeout = 30L
    var wait: WebDriverWait = WebDriverWait(dv, Duration.ofSeconds(timeout))

    /*
    Usando o page factory do Appium client, pra definir tempo dos elementos.
     */
    init {
        PageFactory.initElements(AppiumFieldDecorator(dv, Duration.ofSeconds(timeout)), this)
    }

    /**
     * A função visit realiza o click e espera a condição ser possível realizar o clique.
     * @param urlSite url parcial do site.
     */
    fun visit(urlSite: String){
        if(urlSite.contains("https")) {  dv.get(urlSite) }
        else { dv.get(baseURL + urlSite) }
    }

    /**
     * A função click realiza o click e espera a condição ser possível realizar o clique.
     * @param elem passa o elemento mapeado no factory.
     */
    fun click(elem: WebElement){
        val element = wait.until(ExpectedConditions.elementToBeClickable(elem))
        Actions(dv).moveToElement(element).click().build().perform()
    }

    fun click(cssSelector: String){
        val element = find(cssSelector)
        Actions(dv).moveToElement(element).click().build().perform()
    }

    /**
     * A função find realiza busca e espera a condição do elemento ser visível.
     * @param element passa o elemento mapeado no factory.
     * @param focus passar true para focar no elemento, false é o padrão.
     */
    fun find(element: WebElement, focus: Boolean = false): WebElement {
        if (focus) Actions(dv).moveToElement(element).build().perform()
        return wait.until(ExpectedConditions.visibilityOf(element))!!
    }

    /**
     * A função find realiza busca via css seletor e espera a condição do elemento ser visível.
     * @param cssSelector passa o elemento mapeado no factory.
     * @param focus passar true para focar no elemento, false é o padrão.
     */
    fun find(cssSelector: String, focus: Boolean = false): WebElement {
        if (focus) {
            scrollIntoView(cssSelector)
        }
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssSelector)))!!
    }

    /**
     * A função find realiza busca e espera a condição do elemento ser visível.
     * @param element passa o elemento mapeado no factory.
     * @param text passar o texto que preencher no campo.
     * @param focus passar true para focar no elemento, false é o padrão.
     */
    fun sendKeys(element: WebElement, text: String, focus: Boolean = false) {
        if (focus) Actions(dv).moveToElement(element).build().perform()
        wait.until(ExpectedConditions.visibilityOf(element))?.sendKeys(text)
    }

    /**
     * A função contains verifica se o elemento contém o texto esperado.
     * @param textElement passar o texto que vai validar no elemento.
     */
    fun WebElement.contains(textElement: String) {
        when (this.text.contains(textElement)) {
            true -> println("Elemento encontrado na página.")
            else -> fail("Assert Falhou -> ${this.text}")
        }
    }


    /**
     * A função trigger faz o trigger quando o elemento espera um determinado evento.
     * @param cssSelector passar o css selector para a funcao.
     * @param comando passar o evento que deseja fazer o trigger.
     */
    fun trigger(cssSelector: String, comando: String, timeout: Long = 2) {
        val inputStream: InputStream = File("$path\\src\\main\\kotlin\\com\\seleniumvscypress\\core\\jquery\\jquery-3.6.0.js").inputStream()
        val inputString = inputStream.bufferedReader().use { it.readText() }
        (dv as JavascriptExecutor).executeScript(inputString)
        sleep(timeout * 1000) // tempo realizar a leitura do arquivo.
        (dv as JavascriptExecutor).executeScript("return $('$cssSelector').trigger(\"$comando\")")
    }

    /**
     * A função type realiza o envio do texto para o elemento.
     * @param text passar o texto que vai validar no elemento.
     */
    fun WebElement.type(text: String) {
        sendKeys(this, text)
    }

    /**
     * A função type realiza o envio do texto para o elemento.
     * @param text passar o texto que vai validar no elemento.
     */
    fun WebElement.selectByText(text: String) {
        Select(this).selectByVisibleText(text)
        if (this.findElement(By.cssSelector("option:checked")).
            text==text) {
            println("Elemento selecionado com sucesso")
        } else {
            fail("Teste falhou ao selecionar o elemento")
        }
    }

    /**
     * A função selectByValue a seleção do combo por value.
     * @param value passar o texto que vai validar no elemento.
     */
    fun WebElement.selectByValue(value: String) {
        Select(this).selectByValue(value)
        if (this.findElement(By.cssSelector("option:checked")).
            getAttribute("value")==value) {
            println("Elemento selecionado com sucesso")
        } else {
            fail("Teste falhou ao selecionar o elemento")
        }
    }

    /**
     * A função clickJS realiza o click via javascript.
     * @param element passa o elemento mapeado no factory.
     */
    fun clickJavaScript(element: WebElement) {
        (dv as JavascriptExecutor).executeScript("arguments[0].click();", element)
    }

    /**
     * A função scrollIntoView realiza o click via javascript.
     * @param element passa o elemento mapeado no factory.
     */
    fun scrollIntoView(cssSelector: String, timeout: Long = 2) {
        (dv as JavascriptExecutor).executeScript(
            "document.querySelector('$cssSelector').scrollIntoView();")
        sleep(timeout * 1000) // tempo realizar o scroll.
    }

    /**
     * A função selectByVisibleText faz a seleção do combo passando o elemento mapeado e o texto.
     * @param element passar o elemento mapeado.
     * @param text passar o texto visível que deseja selecionar.
     */
    fun selectByVisibleText(element: WebElement, text: String) {
        clickJavaScript(element)
        Select(element).selectByVisibleText(text)
    }

    /**
     * A função takeScreen tira a foto via selenium e anexo no reporte do allure.
     * @param screenName passar o nome da tela ou sem vai pegar o valor padrão.
     */
    fun takeScreen(screenName: String = "TestScreen"){
        Allure.addAttachment(
            screenName, ByteArrayInputStream((dv as TakesScreenshot).getScreenshotAs(OutputType.BYTES))
        )
    }

    /**
     * A função alert espera o alerta esta presente e passa o foco para o mesmo.
     */
    fun alert(): Alert {
        wait.until(ExpectedConditions.alertIsPresent())
        return dv.switchTo().alert()
    }

    /**
     * A função frameId espera o Iframe esta presente e passa o foco para o mesmo.
     */
    fun frameIndex(frameid: Int) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameid))
    }

    fun acceptPopup() {
        dv.switchTo().alert().accept()
    }

}

/*
fun main(){
    val dv = BrowserConfig().setChrome()
    val base = BasePage(dv)
    base.visit("/")
}*/
