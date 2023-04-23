package bookSites;

import dto.BookCommonInfo;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import properties.SeleniumProperties;
import utils.UtilsMethods;

import java.util.List;;

import static utils.UtilsMethods.IsElementExists;

@Slf4j
public class AO3 extends SeleniumBase implements SitesStep {

    public AO3(SeleniumProperties seleniumProperties) {
        super(seleniumProperties);
    }

    @Override
    public void goBeginPageForAlgorithm(String url) {
        driver.navigate().to(url);
        log.info("Совершон переход на начальную страницу: " + url);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header")));
        log.info("Base page was loaded");
    }

    @Override
    public void nextPage() {
        var rootElem = driver.findElement(By.id("feedback"));
        var buttons = rootElem.findElements(By.tagName("li"));
        for (var btn : buttons) {
            if ("Next Chapter →".equals(btn.getText())) {
                log.info("To next chapter");
                btn.click();
                return;
            }
        }
    }

    @Override
    public String getTitle() {
        var elem = By.tagName("h3");
        if (IsElementExists(By.id("chapters"), driver)) {
            var rootElem = driver.findElement(By.id("chapters"));
            System.out.println(rootElem.findElement(elem).getText());
            return rootElem.findElement(elem).getText();
        }
        return "";
    }

    @Override
    public String getText() {
        var rootElem = driver.findElement(By.id("chapters"));
        List<WebElement> paragraphs = rootElem.findElements(By.tagName("p"));
        StringBuilder fullTextFromPage = new StringBuilder();
        for (WebElement par : paragraphs) {
            String textWithHTMLCode = par.getAttribute("innerHTML");
            String textFromParagraph = UtilsMethods.transformHTMLToFb2Tags(textWithHTMLCode);
            if (textFromParagraph.equals("<hr>")) {//их надо самому отслеживать(((
                textFromParagraph = textWithHTMLCode.replaceAll("\\<hr\\>", "<p>***</p>");//"<empty-line/>"); work???
            }
            fullTextFromPage.append(textFromParagraph);
        }
        return fullTextFromPage.toString();
    }

    @Override
    public BookCommonInfo getInfo() {
        //  Map<String, String> map = new HashMap<>();
        var rootElem = driver.findElement(By.id("workskin"));
        BookCommonInfo info = new BookCommonInfo();
        info.setTitle(rootElem.findElement(By.tagName("h2")).getText());
        info.setAuthor(driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div[1]/h3/a")).getText());
        info.setAnnotation(driver.findElement(By.tagName("blockquote")).getText());
        return info;
    }

    @Override
    public Boolean isLastPage() {
        WebElement rootElem = driver.findElement(By.id("feedback"));
        return (!IsElementExists(By.linkText("Next Chapter →"), rootElem));
    }

    @Override
    public void goToFirstBookPage() {
        log.info("Begin expire text");
    }
}
