package storage;

import model.Resume;

import java.util.*;

public class ListStorage extends AbstractStorage<Integer> {

    private final List<Resume> resumeList = new ArrayList<>();

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < resumeList.size(); i++) {
            if (resumeList.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }

    @Override
    protected Resume doGet(Integer searchKey) {
        return resumeList.get(searchKey);
    }

    @Override
    protected void doSave(Integer searchKey, Resume resume) {
        resumeList.add(resume);
    }

    @Override
    protected void doUpdate(Integer searchKey, Resume resume) {
        resumeList.set(searchKey, resume);
    }

    @Override
    protected void doDelete(Integer searchKey) {
        resumeList.remove(searchKey.intValue());
    }

    @Override
    public void clear() {
        resumeList.clear();
    }

    @Override
    public List<Resume> doCopyAll() {
        return new ArrayList<>(resumeList);
    }

    @Override
    public int size() {
        return resumeList.size();
    }
}
