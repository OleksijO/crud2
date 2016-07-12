package crud10.dao;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * Loads database driver and parameters for connection,login
 */
public class DatabasePropertiesLoader {
    String driver;
    String url;
    String user;
    String password;
    String propertiesPath;

    public DatabasePropertiesLoader(String path) {
        propertiesPath = path;
        init();
    }

    private void init() {
        Properties properties = new Properties();
        try {
            ClassLoader loader = this.getClass().getClassLoader();
            URL res=loader.getResource(propertiesPath);
            InputStream stream = res.openStream();
            properties = new Properties();
            properties.load(stream);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        this.driver = properties.getProperty("jdbc.driver");
        this.url = properties.getProperty("jdbc.url");
        this.user = properties.getProperty("jdbc.user");
        this.password = properties.getProperty("jdbc.password");
        try {
            Class.forName(this.driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
