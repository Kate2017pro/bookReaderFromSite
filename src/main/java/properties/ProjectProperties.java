package properties;

import lombok.*;
import dto.SitesForReading;

@Getter
@RequiredArgsConstructor
@Builder
public class ProjectProperties {

    private final String fileName;
    private final SitesForReading siteType;
    private final String runType;
    private final SeleniumProperties seleniumProperties;

}


