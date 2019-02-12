package storage;

import exception.StorageException;
import model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {

    private File directory;

    protected abstract void doWrite(Resume r, File file) throws IOException;
    protected abstract Resume doRead(File file) throws IOException;
    protected abstract List<Resume> doReadAll(File directory) throws IOException;
    protected abstract void doDeleteAll(File directory);

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected void doSave(File file, Resume resume) {
        try {
            file.createNewFile();
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("File I/O error!", file.getName(), e);
        }
    }

    @Override
    protected void doUpdate(File file, Resume resume) {
        file.delete();
        doSave(file, resume);
    }

    @Override
    protected void doDelete(File file) {
        file.delete();
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return doRead(file);
        } catch (IOException e) {
            throw new StorageException("File read error!", file.getName(), e);
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        try {
            return doReadAll(directory);
        } catch (IOException e) {
            throw new StorageException("All files read error!", directory.getAbsolutePath(), e);

        }
    }

    @Override
    public void clear() {
        doDeleteAll(directory);
    }

    @Override
    public int size() {
        return directory.list().length;
    }
}
