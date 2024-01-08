package utils;

import dto.BookCommonInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class UtilsMethods {

    /**
     * Преобразует текст из html в пригодный для fb2. общие моменты.
     * @param textWithHTMLCode
     * @return
     */
    public static String transformHTMLToFb2Tags(String textWithHTMLCode) {
        StringBuilder fullTextFromPage = new StringBuilder();
        if (textWithHTMLCode.contains("<em>")) {
            textWithHTMLCode = textWithHTMLCode.replaceAll("\\<em\\>", "<emphasis>");
            textWithHTMLCode = textWithHTMLCode.replaceAll("\\<\\/em\\>", "</emphasis>");
        }
        if (textWithHTMLCode.contains("<i>")) {
            textWithHTMLCode = textWithHTMLCode.replaceAll("\\<i\\>", "<emphasis>");
            textWithHTMLCode = textWithHTMLCode.replaceAll("\\<\\/i\\>", "</emphasis>");
        }
        if (textWithHTMLCode.contains("<b>")) {
            textWithHTMLCode = textWithHTMLCode.replaceAll("\\<b\\>", "<strong>");
            textWithHTMLCode = textWithHTMLCode.replaceAll("\\<\\/b\\>", "</strong>");
        }
        if (textWithHTMLCode.contains("<br>")) {
            if (textWithHTMLCode.equals("<br>")) { //если сам абзац равен пустой строке (те только из тэга) то не добавляем тэги абзаца так как эта пустота обернется в абзац потом
                textWithHTMLCode = "";
            } else {
                textWithHTMLCode = textWithHTMLCode.replaceAll("\\<br\\>", "</p><p>\n");//"<empty-line/>");
            }
        }
        //  if (textWithHTMLCode.equals("<hr>")) {//их надо самому отслеживать((( work??
        //       textWithHTMLCode = textWithHTMLCode.replaceAll("\\<hr\\>", "***");//"<empty-line/>"); work???
        //  }
        textWithHTMLCode = textWithHTMLCode.replace("&nbsp;", " ");
        fullTextFromPage.append("<p>").append(textWithHTMLCode).append("</p>\n");
        return fullTextFromPage.toString();
    }

    public static BookCommonInfo fillBookInfoEmptyValue() {
        BookCommonInfo info = new BookCommonInfo();
        info.setAuthor("Автор");
        info.setAnnotation("Аннотация");
        info.setTitle("Заголовок");
        info.setSrcUrl("URL");
        return info;
    }

    /**
     * \brief Проверяет наличие элемента на странице
     *
     * @param iClassName By.Id("id"), By.CssSelector("selector") и т.д.
     * @return Наличие элемента
     */
    public static Boolean IsElementExists(By iClassName, WebDriver driver) {// в метод передаётся "iClassName" это By.Id("id_elementa"), By.CssSelector("selector") и т.д.
        try {
            driver.findElement(iClassName);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public static Boolean IsElementExists(By iClassName, WebElement driver) {// в метод передаётся "iClassName" это By.Id("id_elementa"), By.CssSelector("selector") и т.д.
        try {
            driver.findElement(iClassName);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * \brief Проверяет видимость элемента на странице.
     *
     * @param iClassName "iClassName" = By.Id("id"), By.CssSelector("selector") и т.д.
     * @return Видимость объекта (видимый/не видимый)
     */
    public static Boolean IsElementVisible(By iClassName, WebDriver driver) {
        try {
            boolean iv = driver.findElement(iClassName).isDisplayed();
            if (iv) {
                return true;
            } else {
                return false;
            }
        } catch (NoSuchElementException e) {
            return false;
        } // если элемент вообще не найден
    }
}
