package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import exception.StorageException;
import model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] resumes = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return resumes[index];
    }

    public void clear() {
        Arrays.fill(resumes, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        int rIndex = getIndex(r.getUuid());
        if (rIndex >= 0) {
            throw new ExistStorageException(r.getUuid());
        } else if (size < resumes.length) {
            writeResume(r, rIndex);
        } else {
            String uuid = r.getUuid();
            throw new StorageException("Resume " + uuid + " can't be written. The storage is full!", uuid);
        }
    }

    public void delete(String uuid) {
        int rIndex = getIndex(uuid);
        if (rIndex >= 0) {
            deleteResume(rIndex);
            resumes[--size] = null;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public void update(Resume resume) {
        int rIndex = getIndex(resume.getUuid());
        if (rIndex < 0) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            resumes[rIndex] = resume;
        }
    }

    /**
     * @return array, contains only Resumes in resumes (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(resumes, 0, size);
    }

    protected abstract int getIndex(String uuid);

    protected abstract void writeResume(Resume resume, int index);

    protected abstract void deleteResume(int index);
}
