package utils;

import lombok.extern.apachecommons.CommonsLog;
import model.User;
import service.RestService;
import service.Services;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Shantanu Roy
 * Date: 22-Jun-20
 * Time: 2:30 PM
 */
@CommonsLog
public class ProjectCleanupHelper {

    private ProjectCleanupHelper() {
    }

    private static final ProjectCleanupHelper instance = new ProjectCleanupHelper();

    private static final boolean CLEANUP_REQUIRED = true;

    private RestService restService = Services.getRestService();

    public static ProjectCleanupHelper getInstance() {
        return instance;
    }

    protected ThreadLocal<Map<String, Boolean>> projectKeys = ThreadLocal.withInitial(HashMap::new);

    public void addProjectForCleanUp(String resourceKey) {
        projectKeys.get().put(resourceKey, CLEANUP_REQUIRED);
    }

    public void projectDeleted(String resourceKey) {
        projectKeys.get().put(resourceKey, !CLEANUP_REQUIRED);
    }

    public void removeProjects() {
        User admin = UserProvider.getInstance().getAuthenticatedUser("admin");
        projectKeys.get()
                .forEach((resourceKey, isRemovalNeeded) -> {
                    if (isRemovalNeeded) {
                        restService.deleteProject(admin, resourceKey)
                                .then()
                                .statusCode(204);
                    }
                });
        projectKeys.get().clear();
    }

    public void closeStore() {
        projectKeys.remove();
    }
}
