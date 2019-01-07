package storage;

import model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage {

    private Map<String, Resume> resumeMap = new TreeMap<>();

    @Override
    protected Object getSearchKey(String uuid) {
        if (resumeMap.containsKey(uuid)) {
            return uuid;
        } else {
            return "";
        }
    }

    @Override
    protected boolean isExist(String uuid, Object searchKey) {
        return (searchKey != "" & resumeMap.containsKey(uuid)
                & resumeMap.containsValue(resumeMap.get(uuid)));
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return resumeMap.get(searchKey);
    }

    @Override
    protected void doSave(Object searchKey, Resume resume) {
        resumeMap.put((String) searchKey, resume);
    }

    @Override
    protected void doUpdate(Object searchKey, Resume resume) {
        resumeMap.put((String) searchKey, resume);
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
    public Resume[] getAll() {
        return resumeMap.values().toArray(new Resume[resumeMap.size()]);
    }

    @Override
    public int size() {
        return resumeMap.size();
    }
}
