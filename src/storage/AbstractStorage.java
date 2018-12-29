package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import model.Resume;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public abstract class AbstractStorage implements Storage {

    private Collection resumeCollection;

    public AbstractStorage(Collection resumeCollection) {
        this.resumeCollection = resumeCollection;
    }

    public AbstractStorage() {
    }

    @Override
    public void clear() {
        resumeCollection.clear();
    }

    @Override
    public void save(Resume r) {
        int index = getIndex(r.getUuid());
        if (index < 0) {
            writeResume(index, r);
        } else {
            throw new ExistStorageException(r.getUuid());
        }
    }

    @Override
    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            writeResume(index, resume);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public Resume get(String uuid) {
        Resume resume = new Resume(uuid);
        int index = getIndex(uuid);
        if (index >= 0) {
            return resume;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            deleteResume(index);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public Resume[] getAll() {
        return (Resume[]) resumeCollection.toArray(new Resume[resumeCollection.size()]);
    }

    @Override
    public int size() {
        return resumeCollection.size();
    }

    protected abstract int getIndex(String uuid);

    protected abstract void writeResume(int index, Resume resume);

    protected abstract void deleteResume(int index);
}
