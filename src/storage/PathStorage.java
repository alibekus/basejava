package storage;

import exception.StorageException;
import model.Resume;
import serialization.SerializationStrategy;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {

    private Path directory;
    private SerializationStrategy serialization;

    protected PathStorage(String dir, SerializationStrategy serialization) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        this.serialization = serialization;
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return Paths.get(directory.toString() + "/" + uuid);
    }

    @Override
    protected boolean isExist(Path searchKey) {
        return Files.exists(searchKey);
    }

    @Override
    protected Resume doGet(Path searchKey) {
        try {
            return serialization.doRead(new BufferedInputStream(Files.newInputStream(searchKey)));
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
                    searchKey.getFileName().toString(), e);
        }
        doUpdate(searchKey, resume);
    }

    @Override
    protected void doUpdate(Path searchKey, Resume resume) {
        try {
            serialization.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(searchKey)));
        } catch (IOException e) {
            throw new StorageException("Path write error", searchKey.getFileName().toString(), e);
        }
    }

    @Override
    protected void doDelete(Path searchKey) {
        try {
            Files.deleteIfExists(searchKey);
        } catch (IOException e) {
            e.printStackTrace();
            throw new StorageException("Path " + searchKey.toString() + " delete error", searchKey.toString());
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        return getPathStream().map(this::doGet).collect(Collectors.toList());
    }

    @Override
    public void clear() {
        getPathStream().forEach(PathStorage.this::doDelete);
    }

    @Override
    public int size() {
        int i = 0;
        i = (int) getPathStream().count();
        return i;
    }

    private Stream<Path> getPathStream() {
        Stream<Path> pathStream;
        try {
            pathStream = Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Directory " + directory.toString() + " read error", null);
        }
        return pathStream;
    }
}
