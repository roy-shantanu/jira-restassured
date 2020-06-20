package utils;

import java.io.File;
import java.net.URL;
import java.util.Objects;

/**
 * User: Shantanu Roy
 * Date: 20-Jun-20
 * Time: 12:53 AM
 */
public class ResourceLoader {

    public File getResource(String filePath) {
        return new File(Objects.requireNonNull(getResourceUrl(filePath)).getPath());
    }

    private URL getResourceUrl(String filePath){
        return ClassLoader.getSystemClassLoader().getResource(filePath);
    }
}
