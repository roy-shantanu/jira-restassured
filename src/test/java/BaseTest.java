import allure.AllureRequestResponseFilter;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.mapper.ObjectMapperType;
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
public class BaseTest {

    private static final String APPLICATION_PROPERTIES = "application.properties";

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() throws IOException {
        RestAssured.baseURI = getBaseUrl();
        RestAssured.filters(new AllureRequestResponseFilter());
        RestAssured.config = RestAssuredConfig
                .config()
                .logConfig(logConfig().enableLoggingOfRequestAndResponseIfValidationFails())
                .objectMapperConfig(new ObjectMapperConfig(ObjectMapperType.GSON))
                .encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));
    }

    private String getBaseUrl() throws IOException {
        InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(APPLICATION_PROPERTIES);
        Properties applicationProperties = new Properties();
        applicationProperties.load(inputStream);
        return applicationProperties.getProperty("scheme") +
                "://" +
                applicationProperties.getProperty("host") +
                "/" +
                applicationProperties.getProperty("path") +
                "/";
    }
}
