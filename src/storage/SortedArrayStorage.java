package storage;

import model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    private static final Comparator<String> RESUME_COMPARATOR
            = (uuid1, uuid2) -> uuid1.compareTo(uuid2);

    @Override
    protected Object getSearchKey(String uuid) {
        String[] uuids = new String[size];
        for (int i = 0; i < size; i++) {
            uuids[i] = resumes[i].getUuid();
        }
        return Arrays.binarySearch(uuids, 0, size, uuid, RESUME_COMPARATOR);
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
