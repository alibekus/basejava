package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import model.Resume;

import java.util.Collection;
import java.util.Iterator;

public abstract class AbstractStorage implements Storage {

    private Collection resumeCollection;

    public AbstractStorage(Collection resumeCollection) {
        this.resumeCollection = resumeCollection;
    }

    public AbstractStorage() {}

    public void clear() {
        resumeCollection.clear();
    }

    public void save(Resume r) {
        if (resumeCollection.contains(r)) {
            throw new ExistStorageException(r.getUuid());
        } else {
            resumeCollection.add(r);
        }
    }

    public Resume get(String uuid) {
        Resume resume = new Resume(uuid);
        if (resumeCollection.contains(resume)) {
            return resume;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public void delete(String uuid) {
        Resume resume = new Resume(uuid);
        if (resumeCollection.contains(resume)) {
            resumeCollection.remove(resume);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public Resume[] getAll() {
        return (Resume[]) resumeCollection.toArray(new Resume[resumeCollection.size()]);
    }

    public int size() {
        return resumeCollection.size();
    }
}
