package storage;

import model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage<Integer> {

    private final Map<Integer, Resume> resumeMap = new HashMap<>();

    @Override
    protected Integer getSearchKey(String uuid) {
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
    protected boolean isExist(Integer searchKey) {
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
    protected Resume doGet(Integer searchKey) {
        return resumeMap.get(searchKey);
    }

    @Override
    protected void doSave(Integer searchKey, Resume resume) {
        resumeMap.put(resume.hashCode(), resume);
    }

    @Override
    protected void doUpdate(Integer searchKey, Resume resume) {
        resumeMap.put(resume.hashCode(), resume);
    }

    @Override
    protected void doDelete(Integer searchKey) {
        resumeMap.remove(searchKey);
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
