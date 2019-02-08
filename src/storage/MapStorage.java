package storage;

import model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage<Resume> {

    private final Map<String, Resume> resumeMap = new HashMap<>();

    @Override
    protected Resume getSearchKey(String uuid) {
        if (resumeMap.size() != 0) {
            for (Resume resume : resumeMap.values()) {
                if (resume.getUuid().equals(uuid)) {
                    return resume;
                }
            }
        }
        return null;
    }

    @Override
    protected boolean isExist(Resume searchKey) {
        if (resumeMap.size() != 0) {
            for (Resume resume : resumeMap.values()) {
                if (resume.equals(searchKey)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected Resume doGet(Resume searchKey) {
        return resumeMap.get(searchKey.getUuid());
    }

    @Override
    protected void doSave(Resume searchKey, Resume resume) {
        resumeMap.put(resume.getUuid(), resume);
    }

    @Override
    protected void doUpdate(Resume searchKey, Resume resume) {
        resumeMap.replace(searchKey.getUuid(), searchKey, resume);
    }

    @Override
    protected void doDelete(Resume searchKey) {
        resumeMap.remove(searchKey.getUuid());
    }

    @Override
    public void clear() {
        resumeMap.clear();
    }

    @Override
    public List<Resume> doCopyAll() {
        return new ArrayList<>(resumeMap.values());
    }

    @Override
    public int size() {
        return resumeMap.size();
    }
}
