package storage;

import model.Resume;

import java.util.*;

/**
 * Version of class with search key as hashcode
 */

public class MapStorage extends AbstractStorage {

    private Map<Integer, Resume> resumeMap = new TreeMap<>();

    @Override
    protected Object getSearchKey(String uuid) {
        if (resumeMap.size() != 0) {
            for (Resume resume: resumeMap.values()) {
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
    public Resume[] getAll() {
        return resumeMap.values().toArray(new Resume[resumeMap.size()]);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> values = Arrays.asList(resumeMap.values().toArray(new Resume[resumeMap.size()]));
        Collections.sort(values);
        return values;
    }

    @Override
    public int size() {
        return resumeMap.size();
    }
}
