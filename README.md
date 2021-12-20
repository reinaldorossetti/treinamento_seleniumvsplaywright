# treinamento_selenium_kotlin
Treinamento em Selenium em Kotlin

Selenium WebDriver é um projeto abrangente que encapsula uma variedade de ferramentas e bibliotecas que permitem a automação do navegador da web. 
O Selenium fornece especificamente uma infraestrutura para a especificação W3C WebDriver - 
uma plataforma e interface de codificação neutra de linguagem compatível com todos os principais navegadores da Web.

### Round 01.
Selenium Kotlin
```kotlin
@Test
fun round1Login(){
    visit("/")
    find("#username", true).type("colbyfayock")
    find("#password", true).type("Password1234")
    find("#log-in", false).click()
    find(".element-header", true).contains("Financial Overview")
}
``` 
Em cypress
```renderscript
describe("Login", () => {
	cy.visit('https://demo.applitools.com/');
	cy.get('#username').type('colbyfayock');
	cy.get('#password').type('Password1234');
	cy.get('.element-header').contains('Financial Overview');
});	
```

### Round 02.
Selenium Kotlin - Selecionar o combobox, pelo value.
```kotlin
@Test
fun round2Select(){
    step('should select an option from the dropdown')
    visit("https://kitchen.applitools.com/ingredients/select")
    find("#spices-select-single", true)
        .selectByValue("ginger")
}
``` 
Em cypress
```renderscript
describe("Login", () => {
    it('should select an option from the dropdown',() => {
	cy.visit('https://kitchen.applitools.com/ingredients/select');
	cy.get('#username')
	    .select('colbyfayock')
	    .should('have.value', 'ginger')
});
});	
```

### Round 03 - Fazendo o upload de uma imagem.
Selenium Kotlin
```kotlin
    @Test
    fun round3FileUpload(){
        visit("https://kitchen.applitools.com/ingredients/file-picker")
        step("should upload a photo to the file picker")
        find("#photo-upload", true)
            .sendKeys("$path/files/cypress-soh-que-nao.PNG")
    }
```
### Round 04 - mudança de tela\iframe.
### Deixei mais complexo, pra mostrar que pode fazer um let dentro de outro, e performático.
Selenium Kotlin
```kotlin
    @Test
    fun round4Iframe_CrossDomain(){
        visit("https://kitchen.applitools.com/ingredients/iframe")
        step("Play de Youtube video.")
        val resultValue = driver.switchTo().frame(1).let {
            find("#player", true).let { it.findElement(By.cssSelector(
                "button[aria-label=Reproduzir]")).click(); it.isDisplayed   }
        }
        assertTrue(resultValue, "falhou ao validar a mudança de iframe")
    }
```

### Round 05 - Trabalhando com esperas e tempo de duração.
### Deixei mais complexo, pra mostrar que pode fazer um let dentro de outro, e performático.
Selenium Kotlin
```kotlin
    @Test
    fun round5WaitForFilter(){
        visit("https://automationbookstore.dev/")
        step("Wait for Filter")
        find("li.ui-screen-hidden", duration = 5)
    }
```


Procurando um elemento:
https://www.selenium.dev/documentation/webdriver/elements/

Uso do selecionar valor no combobox:
https://www.selenium.dev/documentation/webdriver/elements/select_elements/

Actions para mover para o elemento:
https://www.selenium.dev/documentation/webdriver/actions_api/mouse/

Primeira Aula - Realizando a Pesquisar:  
https://www.youtube.com/embed/gGjHkR0lrAQ


