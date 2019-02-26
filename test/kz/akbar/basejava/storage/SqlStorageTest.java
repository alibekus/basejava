package kz.akbar.basejava.storage;

import kz.akbar.basejava.Config;

public class SqlStorageTest extends AbstractStorageTest {

    public SqlStorageTest() {
        super(new SqlStorage(Config.getInstance().getUrl(),Config.getInstance().getUser(),
                Config.getInstance().getPassword()));
    }
}