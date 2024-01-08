package bookSites;

import dto.BookCommonInfo;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import properties.SeleniumProperties;
import utils.UtilsMethods;

import java.util.List;

import static utils.UtilsMethods.IsElementExists;

@Slf4j
public class Wuxia extends SeleniumBase implements SitesStep {

    public Wuxia(SeleniumProperties seleniumProperties) {
        super(seleniumProperties);
    }

    public void goBeginPageForAlgorithm(String url) {
        driver.navigate().to(url);
        log.info("Совершон переход на начальную страницу: " + url);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("text-center")));
        log.info("Base page was loaded");
    }

    public void nextPage() {
        if (IsElementExists(By.className("btn-blue"), driver)) {
            var btns = driver.findElements(By.className("btn-blue"));
            var next= btns.stream().filter(bt -> bt.getText().equals("Вперед")).toList();
            if(next.isEmpty()){
                log.warn("Not btn for click next");
            }
            else {
                var url= next.get(0).getAttribute("href");
                driver.navigate().to(url);
            }
            log.debug("Click кнопка Следующая страница");}
    }

    public Boolean isLastPage() {
        if (IsElementExists(By.className("btn-blue"), driver)) {
            var btns = driver.findElements(By.className("btn-blue"));
            var next = btns.stream().filter(bt -> bt.getText().equals("Вперед")).toList();
            return next.isEmpty();
        }
        return false;
    }

    public String getTitle() {
        if (IsElementExists(By.tagName("h2"), driver)) {
            System.out.println(driver.findElement(By.tagName("h2")).getText());
            return driver.findElement(By.tagName("h2")).getText();
        }
        return "";
    }

    public String getText() {
        var block = driver.findElement(By.className("js-full-content"));
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
