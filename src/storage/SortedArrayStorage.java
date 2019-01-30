package storage;

import model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    private static final Comparator<Resume> RESUME_COMPARATOR
            = (resume1, resume2) -> resume1.compareTo(resume2);

    @Override
    protected Object getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(resumes, 0, size, searchKey, RESUME_COMPARATOR);
    }

    @Override
    protected void writeResume(int index, Resume resume) {
        index = -index - 1;
        System.arraycopy(resumes, index, resumes, index + 1, size - index);
        resumes[index] = resume;
        size++;
    }

    @Override
    protected void deleteResume(int rIndex) {
        int length = size - rIndex - 1;
        if (length > 0) {
            System.arraycopy(resumes, rIndex + 1, resumes, rIndex, length);
        }
    }
}
