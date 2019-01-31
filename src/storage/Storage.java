package storage;

import model.Resume;

import java.util.List;

/**
 * Array based resumes for Resumes
 */
public interface Storage {
    void clear();

    void save(Resume resume);

    void update(Resume resume);

    Resume get(String uuid);

    void delete(String uuid);

    Resume[] getAll();

    List<Resume> getAllSorted();

    int size();
}
