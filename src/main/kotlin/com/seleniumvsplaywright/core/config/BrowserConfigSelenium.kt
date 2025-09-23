package com.seleniumvsplaywright.core.config

import org.openqa.selenium.PageLoadStrategy
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import java.time.Duration

class BrowserConfigSelenium() {

    private val timeout = 30L
    val path: String = System.getProperty("user.dir")

    fun setChrome(): WebDriver {
        val chromeOptions = ChromeOptions().apply {
           setPageLoadStrategy(PageLoadStrategy.NORMAL)
            setAcceptInsecureCerts(true)
            addArguments("--no-sandbox")
            addArguments("--remote-allow-origins=*")
            addArguments("--disable-popup-blocking")
            addArguments("--disable-features=PasswordLeakDetection")
            addArguments("--disable-features=PasswordSaving")
            addArguments("profile.password_manager_leak_detection=false")
        }
        val driver = ChromeDriver(chromeOptions)
        driver.manage().apply {
            timeouts().implicitlyWait(Duration.ofSeconds(timeout))
            timeouts().pageLoadTimeout(Duration.ofSeconds(timeout))
            timeouts().scriptTimeout(Duration.ofSeconds(timeout))
            window().maximize()
        }
        return driver
    }
}


fun main(){
    val driver = BrowserConfigSelenium().setChrome()
    driver.get("https://www.google.com")
}

