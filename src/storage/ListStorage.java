package storage;

import model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private List<Resume> resumeList = new ArrayList<>();

    @Override
    protected void doSave(Resume resume) {
        resumeList.add(resume);
    }

    @Override
    protected void doDelete(String uuid) {
        resumeList.remove(doGet(uuid));
    }

    @Override
    protected String getSearchKey(String uuid) {
        Resume getResume = new Resume(uuid);
        if (resumeList.contains(getResume)) {
            return uuid;
        } else {
            return "";
        }
    }

    @Override
    protected Resume doGet(String uuid) {
        for (Resume resume : resumeList) {
            if (resume.getUuid().equals(uuid)) {
                return resume;
            }
        }
        return null;
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
