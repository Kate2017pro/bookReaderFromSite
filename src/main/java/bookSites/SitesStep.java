package bookSites;

import dto.BookCommonInfo;

import java.util.Map;

public interface SitesStep {

    //начальная страница с которой читаем инфу о книге, самая первая в алгоритме
    void goBeginPageForAlgorithm(String url);

    //переход на след страницу (обычно - главу)
    void nextPage();

    //олучение заголовка
    String getTitle();

    //получение самого текста книги
    String getText();

    //получение инфу о книге todo переделать на дто а не на мапу
    BookCommonInfo getInfo();

    //определение последнея ли страница книги
    Boolean isLastPage();

    //первая страница книги
    void goToFirstBookPage() throws Exception;

    void tearDown();
}
