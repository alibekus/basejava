package storage;

import exception.StorageException;
import model.Resume;
import storage.serialization.Serialization;

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
    private Serialization serializer;

    protected PathStorage(String dir, Serialization serializer) {
        Objects.requireNonNull(dir, "directory must not be null");
        directory = Paths.get(dir);
        this.serializer = serializer;
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
            return serializer.doRead(new BufferedInputStream(Files.newInputStream(searchKey)));
        } catch (IOException e) {
            throw new StorageException("Path read error", searchKey.getFileName().toString(), e);
        }
    }

    @Override
    protected void doSave(Path searchKey, Resume resume) {
        try {
            Files.createFile(searchKey);
        } catch (IOException e) {
            throw new StorageException("Couldn't create Path " + searchKey.getFileName().toString(),
                    searchKey.getFileName().toString(), e);
        }
        doUpdate(searchKey, resume);
    }

    @Override
    protected void doUpdate(Path searchKey, Resume resume) {
        try {
            serializer.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(searchKey)));
        } catch (IOException e) {
            throw new StorageException("Path write error", searchKey.getFileName().toString(), e);
        }
    }

    @Override
    protected void doDelete(Path searchKey) {
        try {
            Files.delete(searchKey);
        } catch (IOException e) {
            e.printStackTrace();
            throw new StorageException("Path delete error", searchKey.getFileName().toString());
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
        return (int) getPathStream().count();
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
