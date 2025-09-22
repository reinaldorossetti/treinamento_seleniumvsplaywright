package com.seleniumvsplaywright.model

data class DesafioElements(
    val username: String = "#username",
    val password: String = "#password",
    val loginButton: String = "#log-in",
    val headerText: String = ".element-header:nth-of-type(2)",
    val selectSpices: String = "#spices-select-single",
    val fileUploadInput: String = "#photo-upload",
    val iframeID: String = "#youtube-table-cypress",
    val youtubePlayButton: String = "button[aria-label=Reproduzir]",
    val categoryFilterButton: String = "button[aria-label='All Categories']",
    val categoryOption: String = "text=JavaScript",
    val bookItem: String = ".book-item",
    val usernameValue: String = "angie",
    val passwordValue: String = "1234"
)
