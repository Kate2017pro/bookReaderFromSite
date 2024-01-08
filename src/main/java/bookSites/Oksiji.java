package bookSites;

import dto.BookCommonInfo;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import properties.SeleniumProperties;
import utils.UtilsMethods;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static utils.UtilsMethods.IsElementExists;

@Slf4j
public class Oksiji extends SeleniumBase implements SitesStep {

    public Oksiji(SeleniumProperties seleniumProperties) {
        super(seleniumProperties);
    }

    public void goBeginPageForAlgorithm(String url) {
        driver.navigate().to(url);
        log.info("Совершон переход на начальную страницу: " + url);
        driver.navigate().to(url);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("card-header")));
        log.info("Base page was loaded");
    }

    public void nextPage() {
        if (IsElementExists(By.className("btn-outline-chapter-nav"), driver)) {
            var btns = driver.findElements(By.className("btn-outline-chapter-nav"));
           var next= btns.stream().filter(bt -> bt.getText().equals("Следующая глава")).toList();
           if(next.isEmpty()){
               log.warn("Not btn for click next");
           }
           else {
              var url= next.get(0).getAttribute("href");
              driver.navigate().to(url);
           }
            log.debug("Click кнопка Следующая страница");}
    }

    public String getTitle() {
        if (IsElementExists(By.className("card-title"), driver)) {
            System.out.println(driver.findElement(By.className("card-title")).getText());
            return driver.findElement(By.className("card-title")).getText();
        }
        return "";
    }

    public Boolean isLastPage() { //check!!
        if (IsElementExists(By.className("btn-outline-chapter-nav"), driver)) {
            var btns = driver.findElements(By.className("btn-outline-chapter-nav"));
            var next = btns.stream().filter(bt -> bt.getText().equals("Вперед")).toList();
            return next.isEmpty();
        }
        return false;
    }

    public String getText() {
        var block = driver.findElement(By.className("chapter_text"));
        List<WebElement> paragraphs = block.findElements(By.tagName("p"));
        StringBuilder fullTextFromPage = new StringBuilder();
        for (WebElement par : paragraphs) {
            String textWithHTMLCode = par.getAttribute("innerHTML");
            String textFromParagraph = UtilsMethods.transformHTMLToFb2Tags(textWithHTMLCode);
            fullTextFromPage.append(textFromParagraph);
        }
        return fullTextFromPage.toString();
    }


    public void goToFirstBookPage() throws Exception {
        return;
    }

    public BookCommonInfo getInfo() {
        return null;
    }


}
