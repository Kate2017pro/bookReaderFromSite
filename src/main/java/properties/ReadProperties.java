package properties;

import lombok.extern.slf4j.Slf4j;
import dto.DriverTypes;
import dto.SitesForReading;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Slf4j
public class ReadProperties {
    private static final String RESOURCE_PATH = "src/main/resources/";
    private static final String PROPERTY_FILE_NAME = "application.properties";

    /**
     * Читает файл свойст и возвращает стандартный тип для их хранения - Properties
     * @return свойства в стандартном варианте
     */
    private static Properties readProperties() {
        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(RESOURCE_PATH + PROPERTY_FILE_NAME));
            log.info("Прочитан файл свойств проекта: " + RESOURCE_PATH + PROPERTY_FILE_NAME); //debug
        } catch (IOException e) {
            e.printStackTrace();
        }
        return appProps;
    }

    /**
     * Также читает файл свойств, но потом преобразует возвращаемый стандарный Properties во внутренний формат хранения этих свойст.
     * По сути конвектер со чтением
     * @return
     */
    public static ProjectProperties getProjectProperties() {
        var properties = readProperties();
        SeleniumProperties seleniumProperties = SeleniumProperties.builder()
                .login(properties.getProperty("LOGIN"))
                .password(properties.getProperty("PASSWORD"))
                .baseUrl(properties.getProperty("BASE_URL"))
                .driverPath(properties.getProperty("DRIVER_PATH", "Driver\\chromedriver.exe"))
                .driverType(DriverTypes.valueOf(properties.getProperty("DRIVER_TYPE", "chrome").toUpperCase()))
                .build();
        return ProjectProperties.builder()
                .fileName(properties.getProperty("FILE_NAME", "NewBook.fb2"))
                .runType(properties.getProperty("RUN_TYPE", "full"))
                .siteType(SitesForReading.valueOf(properties.getProperty("SITE_TYPE").toUpperCase()))
                .seleniumProperties(seleniumProperties)
                .build();
    }

}
