package storage;

import model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    private static final Comparator<Resume> RESUME_COMPARATOR
            = Comparator.comparing(Resume::getUuid);

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume resume = new Resume(uuid, "");
        int resumeSearch = Arrays.binarySearch(resumes, 0, size, resume, RESUME_COMPARATOR);
        return resumeSearch;
    }

    @Override
    protected void writeResume(int index, Resume resume) {
        index = -index - 1;
        System.arraycopy(resumes, index, resumes, index + 1, size - index);
        resumes[index] = resume;
    }

    @Override
    protected void deleteResume(int index) {
        int length = size - index - 1;
        if (length > 0) {
            System.arraycopy(resumes, index + 1, resumes, index, length);
        }
    }
}
