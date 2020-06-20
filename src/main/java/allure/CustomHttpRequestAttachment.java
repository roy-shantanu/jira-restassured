package allure;

import io.qameta.allure.attachment.AttachmentData;
import io.restassured.specification.MultiPartSpecification;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

/**
 * User: Shantanu Roy
 * Date: 20-Jun-20
 * Time: 4:41 PM
 */
@Getter
@Builder(builderClassName = "Builder")
public class CustomHttpRequestAttachment implements AttachmentData {
    private final String name;

    private final String url;

    private final String method;

    private final String body;

    private final Map<String, String> headers;

    private final Map<String, String> cookies;

    private final Map<String, String> formParams;

    private final Map<String, String> pathParams;

    private final Map<String, String> queryParams;

    private final Map<String, MultiPartSpecification> multiPart;
}
