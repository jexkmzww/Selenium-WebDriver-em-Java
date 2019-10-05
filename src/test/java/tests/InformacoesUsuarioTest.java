package tests;

import static org.junit.Assert.*;

import jdk.nashorn.internal.runtime.ScriptEnvironment;
import org.easetech.easytest.annotation.DataLoader;
import org.easetech.easytest.annotation.Param;
import org.easetech.easytest.runner.DataDrivenTestRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import suporte.Generator;
import suporte.Screenshot;
import suporte.Web;

import java.nio.channels.SelectableChannel;
import java.util.concurrent.TimeUnit;

@RunWith(DataDrivenTestRunner.class)
@DataLoader(filePaths = "InformacoesUsuarioTest.csv")
public class InformacoesUsuarioTest {
    private WebDriver navegador;

    @Rule
    public TestName test = new TestName();

    @Before
    public void setUp (){

        navegador = Web.createChrome();

        WebElement linkSignIn = navegador.findElement(By.linkText("Sign in"));
        linkSignIn.click();

        WebElement formularioSignInBox = navegador.findElement(By.id("signinbox"));
        formularioSignInBox.findElement(By.name("login")).sendKeys("julio0001");
        formularioSignInBox.findElement(By.name("password")).sendKeys("123456");
        formularioSignInBox.findElement(By.linkText("SIGN IN")).click();

        /*WebElement me = navegador.findElement(By.className("me"));
        String textoNoElementoMe = me.getText();
        assertEquals("Hi, Julio", textoNoElementoMe);*/

        navegador.findElement(By.className("me")).click();
        navegador.findElement(By.linkText("MORE DATA ABOUT YOU")).click();
    }

    @Test
    public void testAdicionarUmaInformacaoAdicionalDoUsuario (@Param(name="tipo")String tipo, @Param(name="contato")String contato, @Param(name="mensagem")String mensagemEsperada){
        //clicando no Sign in
        navegador.findElement(By.xpath("//button[@data-target=\"addmoredata\"]")).click();

        WebElement popupAddMoreData = navegador.findElement(By.id("addmoredata"));
        WebElement campoType = popupAddMoreData.findElement(By.name("type"));
        new Select(campoType).selectByVisibleText(tipo);
        popupAddMoreData.findElement(By.name("contact")).sendKeys(contato);
        popupAddMoreData.findElement(By.linkText("SAVE")).click();
        WebElement mensagemPop = navegador.findElement(By.id("toast-container"));
        String mensagem = mensagemPop.getText();
        assertEquals(mensagemEsperada, mensagem);
    }

    @Test
    public void removerUmContatoDeUmUsuario(){

        //clicar pelo xpath //span[text()="foda-se"]/following-sibling::a
        navegador.findElement(By.xpath("//span[text()=\"+1162900939222\"]/following-sibling::a")).click();

        //confirmar a janela js
        navegador.switchTo().alert().accept();

        //validar que a msg Rest in peace, dear phone!
        WebElement mensagemPop = navegador.findElement(By.id("toast-container"));
        String mensagem = mensagemPop.getText();
        assertEquals("Rest in peace, dear phone!", mensagem);

        String screenshotArquivo = "C:\\Users\\jexkmzww\\Documents\\webtest\\test-report" + Generator.dataHoraParaAquivo() + test.getMethodName() +".png";
        Screenshot.tirar(navegador, screenshotArquivo);

        //aguardar 10s pra janela sumir
        WebDriverWait aguardar = new WebDriverWait(navegador, 10);
        aguardar.until(ExpectedConditions.stalenessOf(mensagemPop));

        //deslogar pelo link Logout
        navegador.findElement(By.linkText("Logout")).click();
    }

    @After
    public void tearDown () {
        navegador.quit();
    }
}
