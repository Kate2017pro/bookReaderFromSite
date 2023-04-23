package bookSites;

import dto.BookCommonInfo;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import properties.SeleniumProperties;
import utils.UtilsMethods;

import java.util.List;

import static utils.UtilsMethods.IsElementExists;

@Slf4j
public class Author extends SeleniumBase implements SitesStep {

    public Author(SeleniumProperties seleniumProperties) {
      super(seleniumProperties);
    }
//todo в конце по тем же кнопкам если есть след книга цикла переход на нее - сделать отбивку по тексту конца книги тоже

    public void goBeginPageForAlgorithm(String url) {
        driver.navigate().to(url);
        log.info("Совершон переход на начальную страницу: " + url);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("navbar-right")));
        log.info("Base page was loaded");
    }

    public void nextPage() {
        if (IsElementExists(By.className("next"), driver)) {
          /*  String clickOnRead = driver.findElement(By.className("next-part-link")).getAttribute("href");
            driver.get(clickOnRead);*/
            driver.findElement(By.className("next")).findElement(By.tagName("a")).click();
            log.debug("Click кнопка Следующая страница");
        }
    }

    public String getTitle() {
        if (IsElementExists(By.tagName("h1"), driver)) {
            System.out.println(driver.findElement(By.tagName("h1")).getText());
            return driver.findElement(By.tagName("h1")).getText();
        }
        return "";
    }

    public String getText() {
        List<WebElement> paragraphs = driver.findElements(By.tagName("p"));
        StringBuilder fullTextFromPage = new StringBuilder();
        for (WebElement par : paragraphs) {
            String textWithHTMLCode = par.getAttribute("innerHTML");
            String textFromParagraph = UtilsMethods.transformHTMLToFb2Tags(textWithHTMLCode);
            fullTextFromPage.append(textFromParagraph);
        }
        return fullTextFromPage.toString();
    }

    public BookCommonInfo getInfo() {
        BookCommonInfo info = new BookCommonInfo();
        info.setTitle(driver.findElement(By.tagName("h1")).getText());
        info.setAuthor( driver.findElement(By.className("book-authors")).getText());
        info.setAnnotation(driver.findElement(By.className("annotation")).getText());
        return info;
    }

    public Boolean isLastPage() {
        return (!IsElementExists(By.className("next"), driver));
    }

    public void goToFirstBookPage() throws Exception {
        WebElement tabContext = null;
        try {
            WebElement allTabs = driver.findElement(By.className("nav-tabs"));
            tabContext=allTabs.findElement(By.linkText("Оглавление"));
        } catch (NoSuchElementException e){
            log.error("Не найден таб с оглавлением. Нельзя переключиться на содержание");
            tearDown();
            e.printStackTrace();
        }
        tabContext.click();


        WebElement listOfParts = driver.findElement(By.className("table-of-content"));
        listOfParts.findElements(By.tagName("a")).get(0).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("text-container")));
    }
}
