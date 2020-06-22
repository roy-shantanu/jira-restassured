# jira-restassured

Notes:
1. The server version of jira that has been used supports up to API version 2.
2. A default project is created named "default", and key = "DEF"
3. Default users are created with naming convention "admin" and "nonAdmin"

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
    
6. main/java/customassertions :: see point 7 of test/java at the end about custom assertions.
7. main/resources/schema -> used in schema tests:: if the response has different / more / less / different data type elements than the one described in these schemas, the test will fail, and will tell what was the difference.
8. tpl:: see 5.e
9. users.json -> predefined test users

test/java::
1. BaseTest -> 
    1. loads the application.properties file (see test/resources), this file contains the base properties of the application, e.g base url constructs, default project params.
    2. Builds the base url from properties defined in application.properties file.
    3. makes the properties available through getApplicationProperties (this is used to retrieve the default project id)
    4. configures RA with:
       1. base url, 
       2. log when request fails
       3. Gson as object mapper
       4. don't append "content charset" header automatically if not defined in the request
       5. Add the allure filter that we defined earlier (see 5.main/java/allure)
       
2. Tests contains schema tests, this tests two things
    1. If the positive path request is working fine.
    2. If the response as expected schema, this wil thus throw assertion error when
        1. any name of the param is changed
        2. the type of the data doesn't match
        3. if any new param is added
        4. if any param is missing
        
3. Schema tests has its own suite defined in the `@Test` annotation, these tests can be selected by passing run argument -Psuites=schema
    1. See build.gradle where the argument is queried and test suite xml file is selected
    2. see test/resource/suites where the suits are defined
    
4. In `ProjectCreationTests`, `invalidDataSetProvider` is the data provider for the test projectCreationWithInvalidDataShouldFail
5. The test case of the assignment is assignmentTestCase under `IssueCreationTests`
6. Where there are multiple `assertThat()` **SoftAssertions** is used; without this, if the first assertion fails, the rest of the assertions will not be executed. With soft assertions, all of the assertions will be checked, and then an assertion exception will be thrown with details of what were the miss
matches. (see IssueCreationTests for the usage)
7. In `assignmentTestCase()`,  custom assertion is used since then response model is complex and would result in unreadable assertions, instead a fluent assertion is seen because of the use of custom assertion. (See `GetIssueAssert` for the implementation of custom assert)
8. Finally, the attachment used in the attachment upload cases is stored in test/resource/attachments



     