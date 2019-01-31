package storage;

import model.Resume;

import java.util.*;

public class ListStorage extends AbstractStorage {

    private List<Resume> resumeList = new ArrayList<>();

    @Override
    protected Object getSearchKey(String uuid) {
        for (int i = 0; i < resumeList.size(); i++) {
            if (resumeList.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (int) searchKey >= 0;
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return resumeList.get((int) searchKey);
    }

    @Override
    protected void doSave(Object searchKey, Resume resume) {
        resumeList.add(resume);
    }

    @Override
    protected void doUpdate(Object searchKey, Resume resume) {
        resumeList.set((int) searchKey, resume);
    }

    @Override
    protected void doDelete(Object searchKey) {
        resumeList.remove((int) searchKey);
    }

    @Override
    public void clear() {
        resumeList.clear();
    }

    @Override
    public List<Resume> getAll() {
        return resumeList;
    }

    @Override
    public int size() {
        return resumeList.size();
    }
}
