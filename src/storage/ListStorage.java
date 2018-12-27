package storage;

import exception.NotExistStorageException;
import model.Resume;

import java.util.Arrays;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private List<Resume> resumeList;

    public ListStorage(List resumeList) {
        super(resumeList);
        this.resumeList = resumeList;
    }

    public void update(Resume resume) {
        if (resumeList.contains(resume)) {
            int index = Arrays.binarySearch(resumeList.toArray(), 0, resumeList.size(), resume);
            resumeList.set(index,resume);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }
}
