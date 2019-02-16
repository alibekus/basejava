package storage;

import exception.StorageException;
import model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractPathStorage extends AbstractStorage<Path>{

    private Path directory;

    protected AbstractPathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    protected abstract void doWrite(Resume r, OutputStream os) throws IOException;

    protected abstract Resume doRead(InputStream is) throws IOException;

    @Override
    protected Path getSearchKey(String uuid) {
        return Paths.get(directory.toString() + "/"+ uuid);
    }

    @Override
    protected boolean isExist(Path searchKey) {
        return Files.exists(searchKey);
    }

    @Override
    protected Resume doGet(Path searchKey) {
        try {
            return doRead(new BufferedInputStream(new FileInputStream(searchKey.toFile())));
        } catch (IOException e) {
            throw new StorageException("Path read error", searchKey.toString(), e);
        }
    }

    @Override
    protected void doSave(Path searchKey, Resume resume) {
        try {
            Files.createFile(searchKey);
        } catch (IOException e) {
            throw new StorageException("Couldn't create Path " + searchKey.toString(),
                    resume.getUuid(), e);
        }
        doUpdate(searchKey, resume);
    }

    @Override
    protected void doUpdate(Path searchKey, Resume resume) {
        try {
            doWrite(resume, new BufferedOutputStream(new FileOutputStream(searchKey.toFile())));
        } catch (IOException e) {
            throw new StorageException("Path write error", resume.getUuid(), e);
        }
    }

    @Override
    protected void doDelete(Path searchKey) {
        try {
            Files.deleteIfExists(searchKey);
        } catch (IOException e) {
            e.printStackTrace();
            throw new StorageException("Path delete error", searchKey.toString());
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        List<Resume> resumes;
        try {
            resumes = Files.list(directory).map(this::doGet).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Path read error", directory.toString(), e);
        }
        return resumes;
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(AbstractPathStorage.this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
        int i = 0;
        try {
            i = (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("Path read error", directory.toString(), e);
        }
        return i;
    }
}
