package storage;

import model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private List<Resume> resumeList = new ArrayList<>();

    @Override
    protected Object getSearchKey(String uuid) {
        int index = resumeList.indexOf(new Resume(uuid));
        if (index > 0) {
            return resumeList.get(index);
        } else {
            return null;
        }
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return resumeList.contains(searchKey);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return resumeList.get(resumeList.indexOf(searchKey));
    }

    @Override
    protected void doSave(Object searchKey) {
        resumeList.add((Resume) searchKey);
    }

    @Override
    protected void doUpdate(Object searchKey) {
        resumeList.remove(resumeList.indexOf(searchKey));
        resumeList.add((Resume) searchKey);
    }

    @Override
    protected void doDelete(Object searchKey) {
        resumeList.remove(searchKey);
    }

    @Override
    public void clear() {
        resumeList.clear();
    }

    @Override
    public Resume[] getAll() {
        return resumeList.toArray(new Resume[resumeList.size()]);
    }

    @Override
    public int size() {
        return resumeList.size();
    }
}
