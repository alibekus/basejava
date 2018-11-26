
/**
 * Array based storage for Resumes
 */
class ArrayStorage {
    private static int resumeCount = 0;
    private final Resume[] storage = new Resume[10000];
    // Для проверки содержимого произвольного элемента только что созданного массива
    void printAnyElement() {
        int randomIndex = (int) (Math.random() * 10000);
        System.out.println(randomIndex + " element in ArrayStorage: " + storage[randomIndex]);
    }

    void clear() {
        for (int i = 0; i < resumeCount; i++) {
            storage[i] = null;
        }
    }

    void save(Resume r) {
        switch (resumeCount) {
            case 0:
                storage[0] = r;
                break;
            case 1:
                storage[1] = r;
                break;
            default:
                if (resumeCount < 10000)
                    storage[resumeCount] = r;
        }
        resumeCount++;
    }

    Resume get(String uuid) {
        for (int i = 0; i < resumeCount; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return storage[i];
            }
        }
        Resume emptyResume = new Resume();
        emptyResume.setUuid("No such resume!");
        return emptyResume;
    }

    void delete(String uuid) {
        int deletedIndex = 0;
        switch (resumeCount) {
            case 0:
                break;
            case 1:
                storage[0] = null;
                resumeCount--;
                break;
            default:
                for (int i = 0; i < resumeCount; i++) {
                    if (storage[i].getUuid().equals(uuid)) {
                        storage[i] = null;
                        deletedIndex = i;
                        break;
                    }
                }
                if (deletedIndex < resumeCount - 1) {
                    for (int j = 0; j < resumeCount - deletedIndex - 1; j++) {
                        storage[deletedIndex + j] = storage[deletedIndex + 1 + j];
                    }
                    storage[resumeCount-1] = null;
                }
                resumeCount--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] allResumes = new Resume[resumeCount];
        for (int j = 0; j < resumeCount; j++) {
            allResumes[j] = storage[j];
        }
        return allResumes;
    }

    int size() {
        return storage.length;
    }
}
