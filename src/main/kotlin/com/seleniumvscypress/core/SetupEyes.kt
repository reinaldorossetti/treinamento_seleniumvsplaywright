package com.seleniumvscypress.core

import com.applitools.eyes.BatchInfo
import com.applitools.eyes.selenium.BrowserType
import com.applitools.eyes.selenium.Configuration
import com.applitools.eyes.selenium.Eyes

class SetupEyes {

    fun setUpEyes(): Eyes {
        val eyes: Eyes = Eyes()
        // Initialize eyes Configuration
        val config: Configuration = eyes.configuration

        // You can get your api key from the Applitools dashboard
        config.setApiKey("SUA_API_KEY")

        // create a new batch info instance and set it to the configuration
        //config.setBatch(BatchInfo("Ultrafast Batch"))

        // Add browsers with different viewports
        config.addBrowser(800, 600, BrowserType.CHROME)
        config.addBrowser(700, 500, BrowserType.FIREFOX)
        config.addBrowser(1600, 1200, BrowserType.IE_11)
        config.addBrowser(1024, 768, BrowserType.EDGE_CHROMIUM)
        config.addBrowser(800, 600, BrowserType.SAFARI)

        // Set the configuration object to eyes
        eyes.setConfiguration(config)
        return eyes
    }
}