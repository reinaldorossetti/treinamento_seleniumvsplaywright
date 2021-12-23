# treinamento_selenium_kotlin
Treinamento em Selenium em Kotlin, essa foi uma simples comparação de escrita de código, kotlin estender a biblioteca do Selenium e adicionar um método personalizado, o que pode ser de grande ajudar, para diminuir a escrita de código.

Selenium WebDriver é um projeto abrangente que encapsula uma variedade de ferramentas e bibliotecas que permitem a automação do navegador da web. 
O Selenium fornece especificamente uma infraestrutura para a especificação W3C WebDriver - 
uma plataforma e interface de codificação neutra de linguagem compatível com todos os principais navegadores da Web.


Adicionei a função step para inserir um passo no nosso relatório do allure, mas não faz parte da brincadeira esse step.

### Round 01.
Selenium Kotlin
```kotlin
@Test
fun round1Login(){
    visit("/")
    find("#username", true).type("colbyfayock")
    find("#password", true).type("Password1234")
    click("#log-in")
    find(".element-header", true).contains("Financial Overview")
}
``` 
Com BDD\Gherkin
```kotlin
@Test
fun round1Login(){
    step("Dado que esteja no site")
    visit("/")
    step("Quando realizar o login")
    find("#username", true).type("colbyfayock")
    find("#password", true).type("Password1234")
    click("#log-in")
    step("Então valido o menu principal do site")
    find(".element-header", true).contains("Financial Overview")
}
``` 
Em cypress
```js
describe("Login", () => {
	cy.visit('https://demo.applitools.com/');
	cy.get('#username').type('colbyfayock');
	cy.get('#password').type('Password1234');
	cy.get('#log-in').click();
	cy.get('.element-header').contains('Financial Overview');
});	
```
Aqui não precisamos da instância como no cypress, pois estamos herdando a page base com as funções, mas também podemos usar a instancia do driver.
No meu ponto a escrita em Kotlin é melhor sem ponto e virgula no final, além disso podemos criar tags através do junit 5. Podemos descrever nosso testes utilizando a biblioteca do Allure no formato BDD\Gherkin, sem utilizar a biblioteca do Cucumber que cria mais uma camada desnecessária.

### Round 02.
Selenium Kotlin - Selecionar o combobox, pelo value.
```kotlin
@Test
fun round2Select(){
    visit("https://kitchen.applitools.com/ingredients/select")
    step("Selecionando o dropbox e validando a seleção")
    find("#spices-select-single", true)
        .selectByValue("ginger")
}
``` 
Em cypress
```js
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
        step("Realiza a troca de tela e dar o play no youtube")
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
### Round 06 - Trabalhando com esperas e tempo de duração.
### Deixei somente com duas funções.
Selenium Kotlin
```kotlin
    @Test
    fun round6Alerts01(){
        visit("https://kitchen.applitools.com/ingredients/alert")
        click("#alert-button")
        alert().accept()
        click("#alert-button")
        alert().dismiss()
    }
    
    @Test
    fun round6Alerts02(){
        visit("https://kitchen.applitools.com/ingredients/alert")
        click("#prompt-button")
        alert().apply { sendKeys("nachos"); accept() }
    }
```
### Round 07 - Muda o foco para uma nova janela.
Selenium Kotlin
```kotlin
    @Test
    fun round07_testNewTab(){
        visit("https://kitchen.applitools.com/ingredients/links")
        find("#button-the-kitchen-table").click()
        driver.getWindowHandles().map { driver.switchTo().window(it) }
        assertTrue(find("#fruits-vegetables").isDisplayed())
    }
```

### Round 09 - Teste para dar o printscreen na tela.
Selenium Kotlin
```kotlin
    @Test
    fun round09_testTakeScreen(){
        visit("https://kitchen.applitools.com/ingredients/table")
        find("#column-button-name").click()
        takeScreen()
        assertTrue(find("#fruits-vegetables").isDisplayed())
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


