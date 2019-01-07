package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    List<Resume> resumeList = new ArrayList<>();

    @Override
    public void clear() {
        resumeList.clear();
    }

    @Override
    public void save(Resume resume) {
        if (!resumeList.contains(resume)) {
            resumeList.add(resume);
        } else {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void update(Resume resume) {
        if (resumeList.contains(resume)) {
            resumeList.remove(resumeList.indexOf(new Resume(resume.getUuid())));
            resumeList.add(resume);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public Resume get(String uuid) {
        if (resumeList.contains(new Resume(uuid))) {
            return resumeList.get(resumeList.indexOf(new Resume(uuid)));
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void delete(String uuid) {
        if (resumeList.contains(new Resume(uuid))) {
            resumeList.remove(new Resume(uuid));
        } else {
            throw new NotExistStorageException(uuid);
        }
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
