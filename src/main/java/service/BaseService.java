package service;

import io.restassured.builder.RequestSpecBuilder;

/**
 * User: Shantanu Roy
 * Date: 20-Jun-20
 * Time: 12:49 PM
 */
public abstract class BaseService {

    protected abstract String apiName();

    protected abstract String apiVersion();

    protected RequestSpecBuilder baseSpecBuilder() {
        return new RequestSpecBuilder()
                .setBasePath(apiName() + "/" + apiVersion());
    }
}
