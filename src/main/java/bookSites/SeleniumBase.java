package bookSites;

import execeptions.SeleniumWorkExecption;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import properties.SeleniumProperties;
import dto.DriverTypes;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SeleniumBase {

    private final static long DEFAULT_TIMEOUT = 10;
    private static final Map<DriverTypes, String> DRIVER_KEYS = Map.of(
            DriverTypes.CHROME, "webdriver.chrome.driver");

    protected WebDriverWait wait;
    protected WebDriver driver;
    protected final SeleniumProperties seleniumProperties;

    SeleniumBase(SeleniumProperties seleniumProperties) {
        this.seleniumProperties = seleniumProperties;
        if (Files.notExists(Path.of(seleniumProperties.getDriverPath()))) {
            throw new SeleniumWorkExecption("Не доступен драйвер браузера по пути: " + seleniumProperties.getDriverPath());
        }
        System.setProperty(DRIVER_KEYS.get(seleniumProperties.getDriverType()), seleniumProperties.getDriverPath());
        driver = getDriver(seleniumProperties.getDriverType());
        driver.manage().timeouts().setScriptTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
        log.debug("Установлено время для ожидания загрузки: " + DEFAULT_TIMEOUT);
    }

    private WebDriver getDriver(DriverTypes driverType) {
        switch (driverType) {
            case CHROME:
                log.info("Будет создан экзэмпляр драйвера хром-браузера");  //debug
                return new ChromeDriver();
            default:
                throw new SeleniumWorkExecption("Не поддерживаемый тип браузера: " + driverType.toString());
        }
    }

    public void tearDown() {
        if (driver != null) {
            driver.quit();
            log.info("Работа браузера завершена");
            driver = null;
        }
    }

}
