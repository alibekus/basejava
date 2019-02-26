package kz.akbar.basejava;

import java.io.*;
import java.util.Properties;

public class Config {

    private static final File PROPS = new File("config\\db.properties");
    private static final Config INSTANCE = new Config();
    private Properties properties = new Properties();
    private File storageDir;
    private String url;
    private String user;
    private String password;

    public static Config getInstance() {
        return INSTANCE;
    }

    private Config() {
        try(InputStream is = new FileInputStream(PROPS)) {
            properties.load(is);
            storageDir = new File(properties.getProperty("storage.dir"));
            url = properties.getProperty("db.url");
            user = properties.getProperty("db.user");
            password = properties.getProperty("db.password");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new IllegalStateException("Invalid config file " + PROPS.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getStorageDir() {
        return storageDir;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getUrl() {
        return url;
    }
}
