package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {

    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    protected abstract void doSave(SK searchKey, Resume resume);

    protected abstract void doUpdate(SK searchKey, Resume resume);

    protected abstract void doDelete(SK searchKey);

    protected abstract SK getSearchKey(String uuid);

    protected abstract boolean isExist(SK searchKey);

    protected abstract Resume doGet(SK searchKey);

    protected abstract List<Resume> doCopyAll();

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        SK searchKey = checkExistKey(uuid);
        return doGet(searchKey);
    }

    @Override
    public void save(Resume resume) {
        LOG.info("Save " + resume);
        SK searchKey = checkNotExistKey(resume.getUuid());
        doSave(searchKey, resume);
    }

    @Override
    public void update(Resume resume) {
        LOG.info("Update " + resume);
        SK searchKey = checkExistKey(resume.getUuid());
        doUpdate(searchKey, resume);
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        SK searchKey = checkExistKey(uuid);
        doDelete(searchKey);
    }

    public List<Resume> getAllSorted() {
        List<Resume> resumes = doCopyAll();
        LOG.info("Get all sorted " + resumes);
        Collections.sort(resumes);
        return resumes;
    }

    private SK checkNotExistKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            LOG.info("Get search key " + searchKey);
            return searchKey;
        } else {
            throw new ExistStorageException(uuid);
        }
    }

    private SK checkExistKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            LOG.info("Get search key " + searchKey);
            return searchKey;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }
}
