package storage;

import model.Resume;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class MapStorage extends AbstractStorage {

    private Map<String, Resume> resumeMap;

    public MapStorage(Map resumeMap) {
        super(resumeMap.entrySet());
        this.resumeMap = resumeMap;
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
    protected int getIndex(String uuid) {
        Set<String> keys = resumeMap.keySet();
        return Arrays.binarySearch(keys.toArray(), 0, resumeMap.size(), uuid);
    }

    @Override
    protected void writeResume(int index, Resume resume) {
        resumeMap.put(resume.getUuid(), resume);
    }

    @Override
    protected void deleteResume(int index) {
        Set<String> keySet = resumeMap.keySet();
        String[] keys = keySet.toArray(new String[keySet.size()]);
        String deleteKey = keys[index];
        resumeMap.remove(deleteKey);
    }
}
