package kz.akbar.basejava;

import kz.akbar.basejava.storage.SqlStorage;
import kz.akbar.basejava.storage.Storage;

import java.io.*;
import java.util.Properties;

public class ConfigLocal {

    private static final File PROPS = new File(getHomeDir(), "config\\db.properties");
    private static final ConfigLocal INSTANCE = new ConfigLocal();
    private Storage storage;
    private File storageDir;

    public static ConfigLocal getInstance() {
        return INSTANCE;
    }

    private ConfigLocal() {
        try (InputStream is = new FileInputStream(PROPS)) {
            Properties properties = new Properties();
            properties.load(is);
            storageDir = new File(properties.getProperty("storage.dir"));
            String url = properties.getProperty("db.url");
            String user = properties.getProperty("db.user");
            String password = properties.getProperty("db.password");
            storage = new SqlStorage(url, user, password);
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

    public Storage getStorage() {
        return storage;
    }

    private static File getHomeDir() {
        String prop = System.getProperty("homeDir");
        File homeDir = new File(prop == null ? "." : prop);
        if (!homeDir.isDirectory()) {
            throw new IllegalStateException(homeDir + " is not directory");
        }
        System.out.println("homeDir: " + homeDir);
        return homeDir;
    }
}
