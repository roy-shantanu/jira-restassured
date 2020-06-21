import allure.AllureRequestResponseFilter;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.mapper.ObjectMapperType;
import lombok.extern.apachecommons.CommonsLog;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static io.restassured.config.EncoderConfig.encoderConfig;
import static io.restassured.config.LogConfig.logConfig;

/**
 * User: Shantanu Roy
 * Date: 19-Jun-20
 * Time: 8:59 PM
 */
@CommonsLog
public class BaseTest {
    private static final String APPLICATION_PROPERTIES = "application.properties";
    private Properties applicationProperties;

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        loadApplicationProperties();
        RestAssured.baseURI = getBaseUrl();
        RestAssured.filters(new AllureRequestResponseFilter());
        RestAssured.config = RestAssuredConfig
                .config()
                .logConfig(logConfig().enableLoggingOfRequestAndResponseIfValidationFails())
                .objectMapperConfig(new ObjectMapperConfig(ObjectMapperType.GSON))
                .encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));
    }

    protected Properties getApplicationProperties() {
        if (applicationProperties == null) {
            loadApplicationProperties();
        }
        return applicationProperties;
    }

    private void loadApplicationProperties() {
        InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(APPLICATION_PROPERTIES);
        applicationProperties = new Properties();
        try {
            applicationProperties.load(inputStream);
        } catch (IOException e) {
            log.error(e);
        }
    }

    private String getBaseUrl() {
        return applicationProperties.getProperty("scheme") +
                "://" +
                applicationProperties.getProperty("host") +
                "/" +
                applicationProperties.getProperty("path") +
                "/";
    }
}
