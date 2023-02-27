
import com.github.javafaker.Faker;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Order {

    @Test
    public void navigate() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.get("http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx");

                String username = "Tester";
                String password = "test";
                Thread.sleep(500);

        driver.findElement(By.name("ctl00$MainContent$username")).sendKeys(username);
        driver.findElement(By.name("ctl00$MainContent$password")).sendKeys(password);
        driver.findElement(By.name("ctl00$MainContent$login_button")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//a[@href='Process.aspx']")).click();
        int randomNumber = 1 + (int) (Math.random() * 99);
        String random = String.valueOf(randomNumber);
        driver.findElement(By.name("ctl00$MainContent$fmwOrder$txtQuantity")).sendKeys(random);
        driver.findElement(By.xpath("//input[@value='Calculate']")).click();

        int num = 0;
        if(randomNumber<10){
            num += randomNumber*100;
        }else{
            num += randomNumber * 92;
        }

        String number = String.valueOf(num);
        Thread.sleep(500);
        WebElement total = driver.findElement(By.name("ctl00$MainContent$fmwOrder$txtTotal"));
        Assert.assertEquals(number,total.getAttribute("value"));


        Faker faker = new Faker();
        String firstAndLastName = faker.address().firstName() + " " + faker.address().lastName();
        int randomZip = 10000+(int)(Math.random()*90000);
        String zip = String.valueOf(randomZip);
        String address = faker.address().streetAddress();
        String city = faker.address().city();
        String state = faker.address().state();

        Thread.sleep(500);
        driver.findElement(By.name("ctl00$MainContent$fmwOrder$txtName")).sendKeys(firstAndLastName);
        driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox2")).sendKeys(address);
        driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox3")).sendKeys(city);
        driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox4")).sendKeys(state);
        driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox5")).sendKeys(zip);


        long visaAndMC = (long)(Math.random()*1000000000000000L);
        long amex = (long)(Math.random()*100000000000000L);
        String visa = "4" + String.valueOf(visaAndMC);
        String masterCard = "5" + String.valueOf(visaAndMC);
        String americanExpress = "3" + String.valueOf(amex);
        int randomMonth = 3+ (int)(Math.random()*9);
        int randomYear = 23 + (int)(Math.random()* 33);

        String zero ="";
        if(randomMonth<10){
            zero = "0"+randomMonth;
        }else{
            zero = ""+randomMonth;
        }

        String expDate = zero + "/" + randomYear;

        List <WebElement> list = driver.findElements(By.name("ctl00$MainContent$fmwOrder$cardList"));

        Random ran = new Random();
        WebElement cardNumber = list.get(ran.nextInt(list.size()));
        cardNumber.click();

        String enterCardNumber = "";
        if(cardNumber.getAttribute("id").contains("0")){
            enterCardNumber = visa;
        } if(cardNumber.getAttribute("id").contains("1")){
            enterCardNumber = masterCard;
        }if(cardNumber.getAttribute("id").contains("2")){
            enterCardNumber = americanExpress;
        }
//
        driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox6")).sendKeys(enterCardNumber);
        driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox1")).sendKeys(expDate);
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_InsertButton")).click();

        String pageSource = driver.getPageSource();
        String text = "New order has been successfully added";
        Assert.assertTrue(pageSource.contains(text));
        driver.findElement(By.xpath("//a[@href='Default.aspx']")).click();

        Thread.sleep(2000);
        List<WebElement> row = driver.findElements(By.xpath("//tbody//tr[2]"));

//        List<String> rowOne = new ArrayList<>();
//        for(WebElement element : row){
//            rowOne.add(element.getText());
//        }
//        String elementOne = rowOne.get(1);
//        Assert.assertEquals(elementOne, firstAndLastName);

        WebElement nameCheck = driver.findElement(By.xpath("//tbody//tr[2]//td[2]"));
        Assert.assertEquals(nameCheck.getText(), firstAndLastName);
        WebElement dateCheck = driver.findElement(By.xpath("//tbody//tr[2]//td[6]"));
        Assert.assertEquals(dateCheck.getText(), address);
        WebElement cityCheck = driver.findElement(By.xpath("//tbody//tr[2]//td[7]"));
        Assert.assertEquals(cityCheck.getText(), city);
        WebElement stateCheck = driver.findElement(By.xpath("//tbody//tr[2]//td[8]"));
        Assert.assertEquals(stateCheck.getText(), state);
        WebElement zipCheck = driver.findElement(By.xpath("//tbody//tr[2]//td[9]"));
        Assert.assertEquals(zipCheck.getText(), zip);
        WebElement carNumberCheck = driver.findElement(By.xpath("//tbody//tr[2]//td[11]"));
        Assert.assertEquals(carNumberCheck.getText(), enterCardNumber);
        WebElement carExpCheck = driver.findElement(By.xpath("//tbody//tr[2]//td[12]"));
        Assert.assertEquals(carExpCheck.getText(), expDate);





    }

}
