package allure;

import io.qameta.allure.attachment.DefaultAttachmentProcessor;
import io.qameta.allure.attachment.FreemarkerAttachmentRenderer;
import io.qameta.allure.attachment.http.HttpResponseAttachment;
import io.restassured.filter.FilterContext;
import io.restassured.filter.OrderedFilter;
import io.restassured.internal.NameAndValue;
import io.restassured.internal.support.Prettifier;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import io.restassured.specification.MultiPartSpecification;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * User: Shantanu Roy
 * Date: 20-Jun-20
 * Time: 4:42 PM
 */
public class AllureRequestResponseFilter implements OrderedFilter {
    private String requestTemplatePath = "custom-http-request.ftl";
    private String responseTemplatePath = "http-response.ftl";

    public AllureRequestResponseFilter setRequestTemplate(final String templatePath) {
        this.requestTemplatePath = templatePath;
        return this;
    }

    public AllureRequestResponseFilter setResponseTemplate(final String templatePath) {
        this.responseTemplatePath = templatePath;
        return this;
    }


    @Override
    public Response filter(final FilterableRequestSpecification requestSpec,
                           final FilterableResponseSpecification responseSpec,
                           final FilterContext filterContext) {
        final Prettifier prettifier = new Prettifier();


        final CustomHttpRequestAttachment.Builder requestAttachmentBuilder = CustomHttpRequestAttachment.builder()
                .name("Request to " + requestSpec.getURI())
                .url(requestSpec.getURI())
                .method(requestSpec.getMethod())
                .headers(toMapConverter(requestSpec.getHeaders()))
                .cookies(toMapConverter(requestSpec.getCookies()))
                .formParams(requestSpec.getFormParams())
                .queryParams(requestSpec.getQueryParams())
                .pathParams(requestSpec.getPathParams());

        if (!requestSpec.getMultiPartParams().isEmpty()) {
            Map<String, MultiPartSpecification> multiPartSpecificationMap = new HashMap<>();
            requestSpec.getMultiPartParams()
                    .forEach(mpSpec -> multiPartSpecificationMap.put(mpSpec.getControlName(), mpSpec));
            requestAttachmentBuilder.multiPart(multiPartSpecificationMap);
        }

        if (Objects.nonNull(requestSpec.getBody())) {
            requestAttachmentBuilder.body(prettifier.getPrettifiedBodyIfPossible(requestSpec));
        }

        final CustomHttpRequestAttachment requestAttachment = requestAttachmentBuilder.build();

        new DefaultAttachmentProcessor().addAttachment(
                requestAttachment,
                new FreemarkerAttachmentRenderer(requestTemplatePath)
        );

        final Response response = filterContext.next(requestSpec, responseSpec);
        final HttpResponseAttachment responseAttachment = HttpResponseAttachment.Builder.create(response.getStatusLine())
                .setResponseCode(response.getStatusCode())
                .setHeaders(toMapConverter(response.getHeaders()))
                .setBody(prettifier.getPrettifiedBodyIfPossible(response, response.getBody()))
                .build();

        new DefaultAttachmentProcessor().addAttachment(
                responseAttachment,
                new FreemarkerAttachmentRenderer(responseTemplatePath)
        );

        return response;
    }

    private static Map<String, String> toMapConverter(final Iterable<? extends NameAndValue> items) {
        final Map<String, String> result = new HashMap<>();
        items.forEach(h -> result.put(h.getName(), h.getValue()));
        return result;
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}
