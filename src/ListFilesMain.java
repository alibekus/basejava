import java.io.File;

class ListFilesMain {
    private static void printDirectoryContent(File file) {
        if (!file.isDirectory()) {
            System.out.println(file.getName());
        }
        if (file.isDirectory()) {
            File[] structure = file.listFiles();
            if (structure != null) {
                for (File unit : structure) {
                    if (!unit.getName().equals(".git")) {
                        printDirectoryContent(unit);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        File file = new File(".");
        System.out.println(file.getAbsolutePath());
        printDirectoryContent(file);
    }
}
