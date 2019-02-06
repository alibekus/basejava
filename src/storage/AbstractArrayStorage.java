package storage;

import exception.StorageException;
import model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {

    static final int STORAGE_LIMIT = 10_000;
    final Resume[] resumes = new Resume[STORAGE_LIMIT];

    int size = 0;

    protected abstract void writeResume(int index, Resume resume);

    protected abstract void deleteResume(int index);

    @Override
    protected boolean isExist(Integer searchKey) {
        int index = searchKey;
        return index >= 0;
    }

    @Override
    protected Resume doGet(Integer index) {
        return resumes[index];
    }

    @Override
    public void doSave(Integer index, Resume resume) {
        if (size < resumes.length) {
            writeResume(index, resume);
        } else {
            throw new StorageException("Resume " + resume.getUuid() + " can't be written. " +
                    "The storage is full!", resume.getUuid());
        }
        size++;
    }

    @Override
    protected void doUpdate(Integer index, Resume resume) {
        resumes[index] = resume;
    }

    protected void doDelete(Integer index) {
        deleteResume(index);
        resumes[--size] = null;
    }

    @Override
    public List<Resume> doCopyAll() {
        return Arrays.asList(Arrays.copyOf(resumes, size));
    }

    @Override
    public void clear() {
        Arrays.fill(resumes, 0, size, null);
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }
}
