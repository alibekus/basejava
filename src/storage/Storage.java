package storage;

import model.Resume;

import java.util.List;

public interface Storage {

    int size();

    Resume get(String uuid);

    List<Resume> getAllSorted();

    void save(Resume resume);

    void update(Resume resume);

    void delete(String uuid);

    void clear();
}
