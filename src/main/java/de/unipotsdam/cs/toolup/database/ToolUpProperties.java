package de.unipotsdam.cs.toolup.database;

import java.io.IOException;
import java.util.Properties;

public class ToolUpProperties {

    private static final String KEY_SERVERADRESS = "database server adress";
    private static final String KEY_SERVERPORT = "database server port";
    private static final String KEY_USERNAME = "database user name";
    private static final String KEY_PASSWORD = "database password";
    private static final String KEY_SCHEMA = "database schema";

    private static final String DATABASE_CONNECTOR = "jdbc:mysql://";
    private static String DATABASE_URL = null;
    private static Properties PROPERTIES = null;

    /**
     * Constructs the database url out of the read properties.
     *
     * @return the url of the configured DB
     * @throws IOException
     */
    public static String getDatabaseUrl() throws IOException {
        if (DATABASE_URL == null) {
            DATABASE_URL = DATABASE_CONNECTOR +
                    getProperties().getProperty(KEY_SERVERADRESS) +
                    ":" +
                    getProperties().getProperty(KEY_SERVERPORT) +
                    "/" +
                    getProperties().getProperty(KEY_SCHEMA) +
                    "?user=" +
                    getProperties().getProperty(KEY_USERNAME + "&pw=") +
                    getProperties().getProperty(KEY_PASSWORD);
        }
        return DATABASE_URL;
    }

    /**
     * @return the ToolUp singleton Properties object
     * @throws IOException
     */
    public static Properties getProperties() throws IOException {
        if (PROPERTIES == null) {
            loadPropertiesFromFile();
        }
        return PROPERTIES;
    }

    private static synchronized void loadPropertiesFromFile() throws IOException {
        if (PROPERTIES == null) {
            PROPERTIES = new Properties();
            PROPERTIES.loadFromXML(ToolUpProperties.class.getResourceAsStream("/Tool.UP_cfg.xml"));
        }
    }

}
