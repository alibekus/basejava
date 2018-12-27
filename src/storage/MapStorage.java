package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage {

    private Map<String,Resume> resumeMap;

    public MapStorage(Map resumeMap) {
        super(resumeMap.entrySet());
        this.resumeMap = resumeMap;
    }

    @Override
    public void clear() {
        resumeMap.clear();
    }

    @Override
    public void save(Resume resume) {
        if (!resumeMap.containsValue(resume)) {
            resumeMap.put(resume.getUuid(), resume);
        } else {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    public void update(Resume resume) {
        if (resumeMap.containsValue(resume)) {
            resumeMap.put(resume.getUuid(), resume);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public Resume get(String uuid) {
        if (resumeMap.containsKey(uuid)) {
            return resumeMap.get(uuid);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void delete(String uuid) {
        if (resumeMap.containsKey(uuid)) {
            resumeMap.remove(uuid);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public Resume[] getAll() {
        return resumeMap.values().toArray(new Resume[resumeMap.size()]);
    }
}
