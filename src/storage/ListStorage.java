package storage;

import model.Resume;

import java.util.Arrays;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private List<Resume> resumeList;

    public ListStorage(List resumeList) {
        super(resumeList);
        this.resumeList = resumeList;
    }


    @Override
    protected int getIndex(String uuid) {
        Resume resume = new Resume(uuid);
        return Arrays.binarySearch(resumeList.toArray(), 0, resumeList.size(), resume);
    }

    @Override
    protected void writeResume(int index, Resume resume) {
        if (index < 0) {
            resumeList.add(resume);
        } else {
            resumeList.add(index, resume);
        }
    }

    @Override
    protected void deleteResume(int index) {
        resumeList.remove(index);
    }
}
