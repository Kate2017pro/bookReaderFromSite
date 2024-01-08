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

import static utils.UtilsMethods.IsElementExists;
@Slf4j
public class Ruvers extends SeleniumBase implements SitesStep {

    public Ruvers(SeleniumProperties seleniumProperties) {
        super(seleniumProperties);
    }

    public void goBeginPageForAlgorithm(String url) {
        driver.navigate().to(url);
        log.info("Совершон переход на начальную страницу: " + url);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("catalog_actions_settings")));
        log.info("Base page was loaded");
    }

    public void nextPage() {
        if (IsElementExists(By.className("next"), driver)) {
          /*  String clickOnRead = driver.findElement(By.className("next-part-link")).getAttribute("href");
            driver.get(clickOnRead);*/
            driver.findElement(By.className("next")).click();
            log.debug("Click кнопка Следующая страница");
        }
    }

    public String getTitle() {
        if (IsElementExists(By.className("chapter_select"), driver)) {
            System.out.println(driver.findElement(By.className("chapter_select")).getText());
            return driver.findElement(By.className("chapter_select")).getText();
        }
        return "";
    }

    public String getText() {
        WebElement text = driver.findElement(By.className("inner_text"));
        WebElement paragraphs = text.findElement(By.tagName("div")).findElement(By.tagName("div"));
        StringBuilder fullTextFromPage = new StringBuilder();
        String textWithHTMLCode = paragraphs.getAttribute("innerHTML");
        List<String> pars = Arrays.stream(textWithHTMLCode.split("<br>")).toList();
        for (String par : pars) {
           // String textWithHTMLCode = par.getAttribute("innerHTML");
            String textFromParagraph = UtilsMethods.transformHTMLToFb2Tags(par);
            fullTextFromPage.append(textFromParagraph);
        }
        return fullTextFromPage.toString();
    }

    public Boolean isLastPage() {
        return (!IsElementExists(By.className("next"), driver));
    }


    public void goToFirstBookPage() throws Exception {
        return;
    }

    public BookCommonInfo getInfo() {
        return null;
    }
}
