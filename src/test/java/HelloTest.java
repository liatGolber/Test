import org.junit.After;
import org.junit.internal.runners.statements.Fail;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.NoSuchElementException;

import static org.testng.AssertJUnit.fail;


public class HelloTest {
    String link = "https://run.mocky.io/v3/5f233855-0bc4-487a-ab85-640a2168cb0d";

    @After
    public void After (WebDriver driver) {
        driver.quit();
    }
    @Test
    public void clickNewMOCKTest() throws InterruptedException {
        try {
            // location of chromdriver
            System.setProperty("webdriver.chrome.driver", "resorces/chromedriver/chromedriver.exe");

            //start session
            WebDriver driver = new ChromeDriver();

            //Navigate
            driver.get("https://designer.mocky.io/");

            //Ensure browser in correct state when finding element
            WebDriverWait wait = new WebDriverWait(driver, 10);
            WebElement NewMock = wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("NEW MOCK")));


            //take action of the element
            NewMock.click();

        }catch (NoSuchElementException e){
            fail("element not found");
        }catch (Exception e) {
            fail("Exception was thrown");
        }
    }

    @Test
    public void HeaderBodyValueTest() throws InterruptedException {
        try {
            // location of chromdriver
            System.setProperty("webdriver.chrome.driver", "resorces/chromedriver/chromedriver.exe");

            //start session
            WebDriver driver = new ChromeDriver();

            //Navigate
            driver.get("https://designer.mocky.io/design");

            //set value in HTTP header
            JSONObject JHTTPheader = new JSONObject();
            JHTTPheader.put("name", "Liat");
            WebElement HttpHeader;
            HttpHeader = driver.findElement(By.xpath("/html/body/div/div[2]/section[2]/div/div/div/div/form/div[3]/div/textarea"));
            HttpHeader.sendKeys(JHTTPheader.toString());

//a[@target='_blank'
            //set value in HTTP Responsee Body
            JSONObject JHTTPResponseBody = new JSONObject();
            JHTTPheader.put("LastNAME", "Mulyan");
            WebElement HttpResponsee;
            HttpResponsee = driver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/section[2]/div/div/div/div/form/div[4]/div/textarea"));
            HttpResponsee.sendKeys(JHTTPheader.toString());

            //Press on Generate my HTTP Response button
            WebElement GenrtorHTTPResponse;
            GenrtorHTTPResponse = driver.findElement(By.xpath("//*[@class='btn btn--primary type--uppercase']"));
            GenrtorHTTPResponse.submit();

            // copy the generate link
            JavascriptExecutor js = ((JavascriptExecutor) driver);
            js.executeScript("window.scrollTo(0,350)");


            Thread.sleep(1000);

            //Copy the URL
            List<WebElement> H3elementCollection = driver.findElements(By.tagName("h3"));
            WebElement FoundElement = null;
            for (int i = 0; i < H3elementCollection.size(); i++) {
                if (H3elementCollection.get(i).getText().equals("Your mock is ready!")) {
                    FoundElement = H3elementCollection.get(i);
                    break;
                }

            }

            if (FoundElement == null) {
                //test fail
            }
            WebElement Parent = (WebElement) js.executeScript("return arguments[0].parentNode;", FoundElement);
            WebElement linkWeb = Parent.findElement(By.tagName("a"));
            link = linkWeb.getAttribute("href");
        }catch (NoSuchElementException e){
            fail("element not found");
        }
        catch (Exception e) {
            fail("Exception was thrown");
        }

    }


    @Test
    public void ApiTEST() throws IOException {

        try {
            // Creates an HttpWebRequest for the specified URL.
            //the if statment check if the locar URL was change
            if (link.equals("https://run.mocky.io/v3/5f233855-0bc4-487a-ab85-640a2168cb0d")) {
                fail("The link was not copy!!");
            }
            URL ourUri = new URL(link);
            HttpURLConnection myHttpWebRequest = (HttpURLConnection) ourUri.openConnection();
            //
            if(myHttpWebRequest.getResponseCode()==400){
                fail("Bad Request response status code indicates");
            }
            //HttpWebResponse myHttpWebResponse = (HttpWebResponse)myHttpWebRequest.GetResponse();


            //Create the requst and get the body
            InputStream responseStream = myHttpWebRequest.getInputStream();
            String body = new String(responseStream.readAllBytes(), StandardCharsets.UTF_8);
            responseStream.readAllBytes();


            //write to log file
            Path filePath = Path.of("./logFile.txt");
            String fileName = "output.log";
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(body);

            writer.close();
        }catch (NoSuchElementException e){
            fail("element not found");
        }catch(MalformedURLException e){
            fail(" URL has occurred");
        } catch (Exception e) {
            fail("Exception was thrown");
        }
        }


}
