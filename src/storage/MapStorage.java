package storage;

import model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage {

    private Map<String, Resume> resumeMap = new TreeMap<>();

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

    @Override
    protected void doSave(Resume resume) {
        resumeMap.put(resume.getUuid(), resume);
    }

    @Override
    protected void doDelete(String uuid) {
        resumeMap.remove(uuid);
    }

    @Override
    protected String getSearchKey(String uuid) {
        if (resumeMap.containsKey(uuid)) {
            return uuid;
        } else {
            return "";
        }
    }

    @Override
    protected Resume doGet(String uuid) {
        String[] keys = resumeMap.keySet().toArray(new String[resumeMap.size()]);
        for (String key : keys) {
            if (key.equals(uuid)) {
                return resumeMap.get(key);
            }
        }
        return null;
    }
}
