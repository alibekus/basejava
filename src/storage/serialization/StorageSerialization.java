package storage.serialization;

import model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface StorageSerialization {

    void doWrite(Resume resume, OutputStream os) throws IOException;

    Resume doRead(InputStream is) throws IOException;
}
