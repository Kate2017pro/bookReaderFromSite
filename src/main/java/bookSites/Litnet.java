package bookSites;

import dto.BookCommonInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import properties.SeleniumProperties;

import static utils.UtilsMethods.IsElementExists;

public class Litnet extends SeleniumBase implements SitesStep {


    public Litnet(SeleniumProperties seleniumProperties) {
        super(seleniumProperties);
    }

    @Override
    public void goBeginPageForAlgorithm(String url) {
        driver.navigate().to(url);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("content-wrapper")));
        System.out.println("Base page load");
    }

    private void autorization(){
        String url = driver.getCurrentUrl();
        driver.findElement(By.className("ln_topbar_login")).click();
        driver.findElements(By.className("reg-vkontakte")).get(0).click();
//login
        driver.findElement(By.className("flat_button")).click();
        System.out.println("Авторизация выполнена");
       /* try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }/*
        driver.navigate().refresh();
        System.out.println("Перезагрузка");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.navigate().to(url);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.navigate().refresh();
        System.out.println("Переход обратно");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("content-wrapper")));
    }

    @Override
    public void nextPage() {

    }

    public String getTitle() {
        if (IsElementExists(By.tagName("h2"), driver)) {
            System.out.println(driver.findElement(By.className("reader-text")).findElement(By.tagName("h2")).getText());
            return driver.findElement(By.className("reader-text")).findElement(By.tagName("h2")).getText();
        }
        return "";
    }

    public String getText() {
        return null;
    }

    public BookCommonInfo getInfo() {
        BookCommonInfo info = new BookCommonInfo();
        info.setTitle(driver.findElement(By.tagName("h1")).getText());
        info.setAuthor(driver.findElement(By.className("author")).getText());
        info.setAnnotation(driver.findElement(By.className("tab-content")).findElement(By.className("tab-pane")).getText());
      ////  Map<String, String> map = new HashMap<>();
     //   map.put("title", driver.findElement(By.tagName("h1")).getText());
     //   map.put("annotation", driver.findElement(By.className("tab-content")).findElement(By.className("tab-pane")).getText());
     //   map.put("author", driver.findElement(By.className("author")).getText());
        return info;
    }

    public Boolean isLastPage() {
        return null;
    }

    public void goToFirstBookPage() throws Exception {
        WebElement selectSoder = driver.findElement(By.tagName("select"));//By.className("js-chapter-change"));
        Select choosePart = new Select(selectSoder);
        choosePart.selectByIndex(1);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("reader-text")));
    }
}
