package com.seleniumvsplaywright.core.config

import com.microsoft.playwright.*
import org.testng.annotations.*
import com.microsoft.playwright.Playwright

import java.util.Arrays;


class BrowserConfigPlaywright{

    private val timeout = 30L
    val path: String = System.getProperty("user.dir")
    lateinit var playwright: Playwright
    private lateinit var browser: Browser
    private var context: BrowserContext? = null
    lateinit var pw: Page

    fun setPWBrowser(): Page {
        Playwright.create().use { playwright ->

            val browserName = System.getenv("BROWSER")
            if (browserName == "firefox") {
                browser = playwright.firefox().launch()
            } else if (browserName == "webkit") {
                browser = playwright.webkit().launch()
            } else {
                println("No browser selected, defaulting to Chromium")
                browser = Playwright
                    .create()
                    .chromium()
                    .launch(BrowserType.LaunchOptions()
                        .setHeadless(true)
                        .setArgs(
                            (Arrays.asList("--no-sandbox",
                                "--disable-extensions",
                                "--disable-gpu",
                                "--remote-allow-origins=*"))
                        )
                    )
//                )
            }
            val newPage = browser.newPage()
            newPage.setDefaultTimeout(30000.0)
            newPage.setDefaultNavigationTimeout(30000.0)
            return newPage
        }
    }

    @AfterTest
    fun closeContext() {
        browser.close()
    }
}


/*abstract class PlaywrightTestCase {
    protected var browserContext: BrowserContext? = null
    protected var page: Page? = null

    @BeforeEach
    fun setUpBrowserContext() {
        browserContext = browser.get().newContext()
        page = browserContext.newPage()
    }

    @AfterEach
    fun closeContext() {
        browserContext.close()
    }

    companion object {
        protected var playwright
                : ThreadLocal<Playwright> = ThreadLocal.withInitial<Playwright>(Supplier<Playwright> {
            val playwright: Playwright = Playwright.create()
            playwright.selectors().setTestIdAttribute("data-test")
            playwright
        }
        )

        protected var browser: ThreadLocal<Browser> = ThreadLocal.withInitial<S>(Supplier<S> {
            playwright.get().chromium().launch(
                LaunchOptions()
                    .setHeadless(true)
                    .setArgs(
                        kotlin.collections.mutableListOf<T>(
                            "--no-sandbox",
                            "--disable-extensions",
                            "--disable-gpu"
                        )
                    )
            )
        }
        )

        @AfterAll
        fun tearDown() {
            browser.get().close()
            browser.remove()

            playwright.get().close()
            playwright.remove()
        }
    }
}*/


//fun main(){
//    val pw = BrowserConfigPlaywright().setPWBrowser()
//    pw?.navigate("https://www.google.com")
//    Thread.sleep(5000)
//}

