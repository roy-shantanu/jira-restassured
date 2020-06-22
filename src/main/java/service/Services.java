package service;

/**
 * User: Shantanu Roy
 * Date: 22-Jun-20
 * Time: 2:44 PM
 */
public class Services {

    private Services() {
    }

    public static AuthenticationService getAuthService() {
        return new AuthenticationServiceImpl();
    }

    public static RestService getRestService() {
        return new RestServiceProxy();
    }
}
