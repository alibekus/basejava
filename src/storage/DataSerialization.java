package storage;

import java.io.IOException;

public interface DataSerialization<T> {
    void dataSerialize(T data) throws IOException;
}
