package kz.akbar.basejava.storage.serialization;

import kz.akbar.basejava.exception.StorageException;
import kz.akbar.basejava.model.Resume;

import java.io.*;

public class ObjectSerializer implements Serialization {

    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(resume);
        }
    }

    public Resume doRead(InputStream is) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error read resume", null, e);
        }
    }
}
