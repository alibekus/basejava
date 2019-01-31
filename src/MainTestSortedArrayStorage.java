import model.Resume;
import storage.SortedArrayStorage;
import storage.Storage;

/**
 * Test for your resumes.ArrayStorage implementation
 */
public class MainTestSortedArrayStorage {
    private static final Storage SORTED_ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid1","");
        Resume r2 = new Resume("uuid2","");
        Resume r3 = new Resume("uuid3","");
        Resume r4 = new Resume("uuid4","");
        Resume r5 = new Resume("uuid5","");

        SORTED_ARRAY_STORAGE.save(r4);
        SORTED_ARRAY_STORAGE.save(r1);
        SORTED_ARRAY_STORAGE.save(r3);
        SORTED_ARRAY_STORAGE.save(r2);
        SORTED_ARRAY_STORAGE.save(r5);

        printAll();

        System.out.println("Get r1: " + SORTED_ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + SORTED_ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + SORTED_ARRAY_STORAGE.get("dummy"));


        printAll();
        System.out.println("Delete r1");
        SORTED_ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        System.out.println("Update r3");
        SORTED_ARRAY_STORAGE.update(r3);
        printAll();
        System.out.println("Clear All");
        SORTED_ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + SORTED_ARRAY_STORAGE.size());
    }

    private static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : SORTED_ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
