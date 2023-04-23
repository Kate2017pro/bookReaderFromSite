package bookSites;

import dto.BookCommonInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import properties.SeleniumProperties;
import utils.UtilsMethods;

import java.util.List;

import static utils.UtilsMethods.IsElementExists;
import static utils.UtilsMethods.IsElementVisible;


public class Wattpad extends SeleniumBase implements SitesStep {

    public Wattpad(SeleniumProperties seleniumProperties) {
        super(seleniumProperties);
/*        driver.navigate().to(url);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header-container")));
        System.out.println("Base page load");*/
    }

    public void goBeginPageForAlgorithm(String url) {
        driver.navigate().to(url);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header-container")));
        System.out.println("Base page load");
    }

    public void nextPage() {
        if (IsElementExists(By.id("story-part-navigation"), driver)) {
            String clickOnRead = driver.findElement(By.id("story-part-navigation")).findElement(By.tagName("a")).getAttribute("href");
            driver.get(clickOnRead);
            System.out.println("Click кнопка Следующая страница");
        }
    }

    public String getTitle() {
        if (IsElementExists(By.tagName("h1"), driver)) {
            System.out.println(driver.findElement(By.tagName("h1")).getText());
            return driver.findElement(By.tagName("h1")).getText();
        }
        return "";
    }

    //todo проверить этот метод!!! опять что-то с версями драйверов
    private String getPartOfText() {
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<WebElement> strs = driver.findElements(By.className("panel-reading"));
        if (strs.size() == 1) return "";
        return strs.stream().skip(1)
                .flatMap(sa -> sa.findElements(By.tagName("p"))
                        .stream().map(par -> {
                            String textWithHTMLCode = par.getAttribute("innerHTML");
                            String textFromPage = UtilsMethods.transformHTMLToFb2Tags(textWithHTMLCode);
                            if (textFromPage.contains("<num-comment>")) {
                                textFromPage = textFromPage.replaceAll("\\<num-comment\\>.*?\\<\\/num-comment\\>", "</emphasis>"); //?
                            }
                            if (textFromPage.contains("<div class=\"component-wrapper\" id=")) {
                                textFromPage = textFromPage.replaceAll("<div class.+</div>", "");
                            }
                            return textFromPage;
                        }))
                .reduce((s1, s2) -> s1 + s2).orElse("");
    }

    private String getPartOfText2() {
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<WebElement> strs = driver.findElements(By.className("panel-reading"));
        if (strs.size() == 1) return "";
        return strs.stream().skip(1)
                .flatMap(sa -> sa.findElements(By.tagName("p"))
                        .stream().map(q -> {
                            String g;
                            try {
                                g = q.findElement(By.className("num-comment")).getText();
                            } catch (NoSuchElementException e) {
                                System.out.println("  No comments in p");
                                return q.getText();
                            }
                            return q.getText().replace(g, "");
                        }))
                .map(ss -> "<p>" + ss + "</p>").reduce((s1, s2) -> s1 + s2).orElse("");
    }

    public String getText() {
        downByPage();
        int i = 1;
        while (!IsElementVisible(By.className("last-page"), driver)) {
            System.out.println("Page down " + ++i);
            downByPage();
        }
        StringBuilder res = new StringBuilder(getPartOfText());
        return res.toString();
    }

    public String getText2() {
        downByPage();
        downByPage();
        StringBuilder res = new StringBuilder(getPartOfText());
        if (findHiddenClass()) {
            System.out.println(" Text write 1");
            return res.toString();
        } else {
            while (IsElementVisible(By.className("load-more-page"), driver)) {
                driver.get(driver.findElement(By.className("load-more-page")).getAttribute("href"));
                System.out.println("  Click load to more pages");
                String temp = getPartOfText();
                if (temp.isEmpty()) {
                    System.out.println(" Text write 3");
                    return res.toString();
                }
                System.out.println("  Another part save");
                res.append(temp);
            }
        }
        System.out.println(" Text write 2");
        return res.toString();
    }

    public void downByPage() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        //This will scroll the web page till end.
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    private Boolean findHiddenClass() {
        try {
            return driver.findElement(By.className("part-navigation")).
                    findElement(By.className("hidden")).getText().contains("Загрузить больше страниц");
        } catch (NoSuchElementException e) {
            System.out.println(" Not find hidden load btn");
            return Boolean.TRUE;
        }
    }

    public Boolean isLastPage() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement btnNext;
        try {
            btnNext = driver.findElement(By.className("part-navigation"));
        } catch (NoSuchElementException ee) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Xmm not find btn in isLastPage");
            btnNext = driver.findElement(By.className("part-navigation"));
        }
        return btnNext.getText().contains("Закончил(ла) чтение") || btnNext.getText().contains("Продолжение следует");
    }

    public void goToFirstBookPage() throws Exception {
        if (IsElementExists(By.className("on-story-navigate"), driver)) {
            String clickOnRead = driver.findElement(By.className("on-story-navigate")).getAttribute("href");
            driver.navigate().to(clickOnRead);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("parts-container-new")));
            System.out.println("Click кнопка Начать чтение");
        } else {
            System.out.println("Не найдена кнопка Начать чтение");
            throw new Exception();
        }
    }

    public BookCommonInfo getInfo() {
        BookCommonInfo info = new BookCommonInfo();
        info.setTitle(driver.findElement(By.tagName("h1")).getText());
        info.setAuthor(driver.findElements(By.className("send-author-event")).stream().filter(s -> !s.getText().isEmpty())
                .map(WebElement::getText).findFirst().orElse("Автор"));
        info.setAnnotation(driver.findElement(By.tagName("h2")).getText());
    /*    Map<String, String> map = new HashMap<>();
        map.put("title", driver.findElement(By.tagName("h1")).getText());
        map.put("annotation", driver.findElement(By.tagName("h2")).getText());
        map.put("author",
                driver.findElements(By.className("send-author-event")).stream().filter(s -> !s.getText().isEmpty())
                        .map(WebElement::getText).findFirst().orElse("Автор"));*/
        return info;
    }
}
