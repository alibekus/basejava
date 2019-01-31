package storage;

import model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage {

    private Map<Integer, Resume> resumeMap = new HashMap<>();

    @Override
    protected Object getSearchKey(String uuid) {
        if (resumeMap.size() != 0) {
            for (Resume resume : resumeMap.values()) {
                if (resume.getUuid().equals(uuid)) {
                    return resume.hashCode();
                }
            }
        }
        return 0;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        if (resumeMap.size() != 0) {
            for (Resume resume : resumeMap.values()) {
                if (resume.hashCode() == (int) searchKey) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return resumeMap.get(searchKey);
    }

    @Override
    protected void doSave(Object searchKey, Resume resume) {
        resumeMap.put(resume.hashCode(), resume);
    }

    @Override
    protected void doUpdate(Object searchKey, Resume resume) {
        resumeMap.put(resume.hashCode(), resume);
    }

    @Override
    protected void doDelete(Object searchKey) {
        resumeMap.remove(searchKey);
    }

    @Override
    public void clear() {
        resumeMap.clear();
    }

    @Override
    public List<Resume> getAll() {
        return new ArrayList<>(resumeMap.values());
    }

    @Override
    public int size() {
        return resumeMap.size();
    }
}
