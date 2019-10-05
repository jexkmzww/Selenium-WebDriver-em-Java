package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver navegador) {
        super(navegador);
    }

    public LoginFormPage clicarSignIn(){
        navegador.findElement(By.linkText("Sign in")).click();

        return new LoginFormPage (navegador);

    }

}
