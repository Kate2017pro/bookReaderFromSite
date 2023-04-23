
import bookSites.SitesFactory;
import bookSites.SitesStep;
import dto.BookCommonInfo;
import lombok.extern.slf4j.Slf4j;
import properties.ReadProperties;
import utils.Fb2Writer;
import utils.UtilsMethods;

@Slf4j
public class Main {

    public static void main(String[] args) throws Exception {
        var projectProperties = ReadProperties.getProjectProperties();
        Fb2Writer fileWr;
        fileWr = new Fb2Writer(projectProperties.getFileName());
        SitesStep implSite = SitesFactory.getSiteByType(projectProperties.getSiteType(), projectProperties.getSeleniumProperties());
        checkTypeForReadingAndRunRead(projectProperties.getRunType().equalsIgnoreCase("full"), implSite,
                fileWr, projectProperties.getSeleniumProperties().getBaseUrl());
    }

    /**
     * Запускает все. Вначале чтение  нфы, переход на первую страницу книги
     * @param isFull
     * @param firstSitePage
     * @param fileWr
     * @param url
     * @throws Exception
     */
    private static void checkTypeForReadingAndRunRead(Boolean isFull, SitesStep firstSitePage, Fb2Writer fileWr, String url) throws Exception {
        firstSitePage.goBeginPageForAlgorithm(url);
        readAndWriteInfoAboutBook(isFull, firstSitePage, fileWr);
        readAndWriteTextFromSite(firstSitePage, fileWr);
    }

    /**
     * Читает инфу о книге или если не требуется заполняет поля филлерами. переходит на первую страницу книги(текста)
     * @param isFull полное чтение или чтение со страницы
     * @param firstSitePage
     * @param fileWr
     * @throws Exception
     */
    private static void readAndWriteInfoAboutBook(Boolean isFull, SitesStep firstSitePage, Fb2Writer fileWr) throws Exception {
        BookCommonInfo info;
        if (isFull) {
            System.out.println("Выбран режим чтения с информацией о книге");
            info = firstSitePage.getInfo();
            firstSitePage.goToFirstBookPage();
        } else {
            System.out.println("Выбран режим чтения с указанной страницы книги");
            info = UtilsMethods.fillBookInfoEmptyValue();
        }
        fileWr.writeDescription(info);
    }

    /**
     * Основной алгоритм. Читает заголовок и текст, записывает пока не закончатся страницы
     * @param sitePage
     * @param fileWr
     */
    private static void readAndWriteTextFromSite(SitesStep sitePage, Fb2Writer fileWr) {
        try {
            fileWr.writePartTitle(sitePage.getTitle());
            fileWr.writeText(sitePage.getText());
            while (!sitePage.isLastPage()) {
                sitePage.nextPage();
                fileWr.writePartTitle(sitePage.getTitle());
                fileWr.writeText(sitePage.getText());
            }
        } catch (Exception e) {
            System.out.println("Exception!!!");
            e.printStackTrace();
        } finally {
            fileWr.endWrite();
            sitePage.tearDown();
        }
    }

}
