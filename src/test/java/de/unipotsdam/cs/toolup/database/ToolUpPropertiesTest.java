package de.unipotsdam.cs.toolup.database;

import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import static org.junit.Assert.assertNotNull;

public class ToolUpPropertiesTest {

    private static final String KEY_SERVERADRESS = "database server adress";
    private static final String KEY_SERVERPORT = "database server port";
    private static final String KEY_USERNAME = "database user name";
    private static final String KEY_PASSWORD = "database password";
    private static final String KEY_SCHEMA = "database schema";

    File PROPERTIES_FILE = new File("Tool.UP_cfg_template.xml");

    /**
     * This method can be used to generate a template cfg file.
     */
//	@BeforeMethod
//	public void prepareConfigFile() throws FileNotFoundException, IOException{
//		Properties props = new Properties();
//		props.put(KEY_SERVERADRESS, VALUE_SERVERADRESS);
//		props.put(KEY_SERVERPORT, VALUE_SERVERPORT);
//		props.put(KEY_USERNAME, VALUE_USERNAME);
//		props.put(KEY_PASSWORD, VALUE_PASSWORD);
//		props.put(KEY_SCHEMA, VALUE_SCHEMA);
//		props.storeToXML(new FileOutputStream(PROPERTIES_FILE), null, "utf-8");
//	}

    @Test
    public void testThatPropertiesAreLoaded() throws IOException {
        //arrange

        //act
        Properties props = ToolUpProperties.getProperties();

        //assert
        assertNotNull(props.getProperty(KEY_SERVERADRESS));
        assertNotNull(props.getProperty(KEY_SERVERPORT));
        assertNotNull(props.getProperty(KEY_USERNAME));
        assertNotNull(props.getProperty(KEY_PASSWORD));
        assertNotNull(props.getProperty(KEY_SCHEMA));
    }

    @Test
    public void testThatDbConnectionIsEstablished() throws Exception {
        //arrange
        String dbUrl = ToolUpProperties.getDatabaseUrl();

        //act
        Connection connection = DriverManager.getConnection(dbUrl);

        //assert
        assertNotNull(connection);
    }

    /**
     * This test is failing on a system with MySQL 5.5,
     * it suceeds on a system with MySQL 5.6.
     * @throws Exception
     */
//    @Test(expectedExceptions = { SQLException.class })
//    public void testThatDbConnectionIsNotEstablishedWithoutValidUsername() throws Exception {
//        //arrange
//        Properties props = ToolUpProperties.getProperties();
//        String dbUrl = ToolUpProperties.getDatabaseUrl();
//        dbUrl = dbUrl.replace(props.getProperty(KEY_USERNAME),"");
//
//        //act
//        Connection connection = DriverManager.getConnection(dbUrl);
//
//        //assert
//    }


}
