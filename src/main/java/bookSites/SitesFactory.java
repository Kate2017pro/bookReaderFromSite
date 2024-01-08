package bookSites;

import execeptions.SeleniumWorkExecption;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import properties.SeleniumProperties;
import dto.SitesForReading;

@NoArgsConstructor
@Slf4j
public class SitesFactory {

    public static SitesStep getSiteByType(SitesForReading siteForReading, SeleniumProperties seleniumProperties) {
        switch (siteForReading) {
            case WATTPAD:
                log.info("Создан объект класса " + Wattpad.class.getName());
                return new Wattpad(seleniumProperties);
            case AUTHOR:
                log.info("Создан объект класса " + Author.class.getName());
                return new Author(seleniumProperties);
            case LITNET:
                log.info("Создан объект класса " + Litnet.class.getName());
                return new Litnet(seleniumProperties);
            case AO3:
                log.info("Создан объект класса " + AO3.class.getName());
                return new AO3(seleniumProperties);
            case RUVERS:
                log.info("Создан объект класса " + Ruvers.class.getName());
                return new Ruvers(seleniumProperties);
            case OKSIJI:
                log.info("Создан объект класса " + Oksiji.class.getName());
                return new Oksiji(seleniumProperties);
            case WUXIA:
                log.info("Создан объект класса " + Wuxia.class.getName());
                return new Wuxia(seleniumProperties);
            default:
                throw new SeleniumWorkExecption("Не поддерживаемый тип сайта (SITE_TYPE): " + siteForReading);
        }
    }
}
