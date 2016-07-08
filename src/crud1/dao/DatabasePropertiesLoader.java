package crud1.dao;

import crud1.controller.DatabaseManagementController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;

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
        this.driver = properties.getProperty("driver");
        this.url = properties.getProperty("url");
        this.user = properties.getProperty("user");
        this.password = properties.getProperty("password");
        try {
            Class.forName(this.driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
