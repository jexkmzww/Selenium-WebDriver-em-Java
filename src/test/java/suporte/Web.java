package suporte;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class Web {
    public static WebDriver createChrome (){

        System.setProperty("webdriver.chrome.driver","C:\\Users\\jexkmzww\\Documents\\chromeDRIVER\\chromedriver.exe");
        WebDriver navegador = new ChromeDriver();
        navegador.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        navegador.manage().window().maximize();
        //abrindo o site
        navegador.get("http://www.juliodelima.com.br/taskit");

        return navegador;
    }
}
