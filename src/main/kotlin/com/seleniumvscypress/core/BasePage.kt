package com.seleniumvscypress.core

import io.appium.java_client.pagefactory.AppiumFieldDecorator
import io.qameta.allure.Allure
import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.support.ui.WebDriverWait
import java.io.ByteArrayInputStream
import java.time.Duration
import kotlin.test.fail


/**
 * Classe BaseCore contém funções globais básicas para as pages.
 * Funções específicas devem esta na Page.
 * Passar o elemento mapeado sempre para a BasePage.
 */
open class Base(var driver: WebDriver) {

    private val baseURL = "https://demo.applitools.com"
    private val timeout = 30L
    private var wait: WebDriverWait = WebDriverWait(driver, Duration.ofSeconds(timeout))
    val path: String = System.getProperty("user.dir")

    /*
    Usando o page factory do Appium client, pra definir tempo dos elementos.
     */
    init {
        PageFactory.initElements(AppiumFieldDecorator(driver, Duration.ofSeconds(timeout)), this)
    }

    /**
     * A função visit realiza o click e espera a condição ser possível realizar o clique.
     * @param urlSite url parcial do site.
     */
    fun visit(urlSite: String){
        if(urlSite.contains("https")) {  driver.get(urlSite) }
        else { driver.get(baseURL + urlSite) }
    }

    /**
     * A função click realiza o click e espera a condição ser possível realizar o clique.
     * @param elem passa o elemento mapeado no factory.
     */
    fun click(elem: WebElement){
        val element = wait.until(ExpectedConditions.elementToBeClickable(elem))
        Actions(driver).moveToElement(element).click().build().perform()
    }

    fun click(cssSelector: String){
        val element = find(cssSelector)
        Actions(driver).moveToElement(element).click().build().perform()
    }

    /**
     * A função find realiza busca e espera a condição do elemento ser visível.
     * @param element passa o elemento mapeado no factory.
     * @param focus passar true para focar no elemento, false é o padrão.
     */
    fun find(element: WebElement, focus: Boolean = false): WebElement {
        if (focus) Actions(driver).moveToElement(element).build().perform()
        return wait.until(ExpectedConditions.visibilityOf(element))
    }

    /**
     * A função find realiza busca via css seletor e espera a condição do elemento ser visível.
     * @param cssSelector passa o elemento mapeado no factory.
     * @param focus passar true para focar no elemento, false é o padrão.
     */
    fun find(cssSelector: String, focus: Boolean = false): WebElement {
        val elem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(cssSelector)))
        if (focus) Actions(driver).moveToElement(elem).build().perform()
        return elem
    }

    fun find(cssSelector: String, focus: Boolean = false, durationMax: Int): WebElement {
        val wait = WebDriverWait(driver, Duration.ofSeconds(durationMax.toLong()))
        val elem = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssSelector)))
        if (focus) Actions(driver).moveToElement(elem).build().perform()
        return elem
    }

    /**
     * A função find realiza busca e espera a condição do elemento ser visível.
     * @param element passa o elemento mapeado no factory.
     * @param text passar o texto que preencher no campo.
     * @param focus passar true para focar no elemento, false é o padrão.
     */
    fun sendKeys(element: WebElement, text: String, focus: Boolean = false) {
        if (focus) Actions(driver).moveToElement(element).build().perform()
        wait.until(ExpectedConditions.visibilityOf(element)).sendKeys(text)
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
        (driver as JavascriptExecutor).executeScript("arguments[0].click();", element)
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
            screenName, ByteArrayInputStream((driver as TakesScreenshot).getScreenshotAs(OutputType.BYTES))
        )
    }

    /**
     * A função alert espera o alerta esta presente e passa o foco para o mesmo.
     */
    fun alert(): Alert {
        wait.until(ExpectedConditions.alertIsPresent())
        return driver.switchTo().alert()
    }

}

/*
fun main(){
    val driver = BrowserConfig().setChrome()
    val base = BasePage(driver)
    base.visit("/")
}*/
