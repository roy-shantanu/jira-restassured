# jira-restassured

Notes:
1. The server version of jira that has been used supports up to API version 2.


Libraries used:
1. **Lombok** (reduce boilerplate, particularly used **NOT** to write getters, setters, constructors, builders and so on) - this is one of my favourite libraries. :heart_eyes:
2. **Gson** as object mapper implementation of restAssured
3. **TestNG** as test runner
4. **assertJ** for readable assertions
5. **lorem** for random text generator
6. **Allure** for reporting

How to navigate through the code:
1. build.gradle -> dependencies, configs and logic for suite resolution
2. main/java/service -> api representations for auth service and the core rest Api of Jira. The service extends base service, which resolves api version and the service path from the two template methods.
3. main/java/model -> contains all the request / response and the data objects concerning the APIs
4. main/java/utils -> contains some handy utility classes
    1. Resource loader - used for loading attachment files, response schemas (any resource files can be loaded)
    2. Request body generator - this generates random request bodies of some cumbersome body params. This returns a builder, so that the request body can be further fine tuned before building. (ex. usage: see invalidDataSetProvider in project creation test)
    3. User provider - loads all tes users that are stored in resources/users.json file, it then can filter and return a particular user when queried, also can make sure the user is authenticated before returning. (UserProvider is made a singleton)
    
5.main/java/allure -> this extends the core allure reporting framework and the there is also a custom request response filter. 
    
    1. RestAssured allows custom filters to be added, which allows to alter the request/response, or execute some code before and after the api call is made.
    b. This fact is utilised to create "Attachment" for Allure. i.e. before each request is made, all the request data are logged as "attachments", and after each request is made, the response data are logged as "attachments". See BaseTest, where this filter is added to RA in a before suite method
    c. The CustomHttpRequestAttachment acts as the request data object. This extends Allure frameworks "Attachment Data"
    d. The AllureRequestResponseFilter is the custom filter that is made, extending RA's ordered filter.
    e. see main/resources/tpl/custom-http-request.ftl -> this is the attachment UI template. (Allure uses freemarker as its template engine)
    
6. main/resources/schema -> used in schema tests:: if the response has different / more / less / different data type elements than the one described in these schemas, the test will fail, and will tell what was the difference.
7. tpl:: see 5.e
8. users.json -> predefined test users



     