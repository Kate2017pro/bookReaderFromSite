package properties;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import dto.DriverTypes;

@Getter
@Builder
@RequiredArgsConstructor
public class SeleniumProperties {
    private final String baseUrl;
    private final String driverPath;
    private final String login;
    private final String password;
    private final DriverTypes driverType;
}
