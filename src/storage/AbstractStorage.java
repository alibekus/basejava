package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import model.Resume;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public abstract class AbstractStorage implements Storage {

    public AbstractStorage() {
    }

    @Override
    public void save(Resume resume) {
        if (!resume.getUuid().equals(getSearchKey(resume.getUuid()))) {
            doSave(resume);
        } else {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void update(Resume resume) {
        if (resume.getUuid().equals(getSearchKey(resume.getUuid()))) {
            doSave(resume);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public Resume get(String uuid) {
        String searchKey = getSearchKey(uuid);
        if (!searchKey.equals(uuid)) {
            throw new NotExistStorageException(uuid);
        }
        return doGet(uuid);
    }

    @Override
    public void delete(String uuid) {
        if (uuid.equals(getSearchKey(uuid))) {
            doDelete(uuid);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract void doSave(Resume resume);

    protected abstract void doDelete(String uuid);

    protected abstract String getSearchKey(String uuid);

    protected abstract Resume doGet(String uuid);
}
