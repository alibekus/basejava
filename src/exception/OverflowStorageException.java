package exception;

public class OverflowStorageException extends StorageException {
    private final String uuid;
    public OverflowStorageException(String uuid) {
        super("Resume " + uuid + " can't be write. The storage is full!", uuid);
        this.uuid = uuid;
    }
}
