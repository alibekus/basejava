import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
    // проверка существования объекта Resume на совпадение по uuid
    boolean exist(Resume resume) {
        for (int i = 0; i < resumeCount; i++) {
            if (storage[i].equals(resume))
                return true;
        }
        return false;
    }

    void save(Resume r) {
        if (!exist(r)) {
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
        } else System.out.println("Такой resume уже есть!");
    }

    void update(Resume resume) {
        if (exist(resume)) {
            for (int i = 0; i < resumeCount; i++)
                if (storage[i].equals(resume)) {
                    System.out.print("Введите новое значение uuid: ");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    try {
                        String newUuid = reader.readLine();
                        Resume r = new Resume();
                        r.setUuid(newUuid);
                        storage[i] = r;
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println(e.getMessage() + "\n Неверно введенное значние!");
                    }

                }
        } else System.out.println("Такого resume нет");
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
        Resume r = new Resume();
        r.setUuid(uuid);
        if (exist(r)) {
            int deletedIndex = 0;
            switch (resumeCount) {
                case 1:
                    storage[0] = null;
                    resumeCount--;
                    break;
                default:
                    for (int i = 0; i < resumeCount; i++) {
                        if (storage[i].equals(r)) {
                            storage[i] = null;
                            deletedIndex = i;
                            break;
                        }
                    }
                    if (deletedIndex < resumeCount - 1) {
                        for (int j = 0; j < resumeCount - deletedIndex - 1; j++) {
                            storage[deletedIndex + j] = storage[deletedIndex + 1 + j];
                        }
                        storage[resumeCount - 1] = null;
                    }
                    resumeCount--;
            }
        } else System.out.println("Такого resume нет!");
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
