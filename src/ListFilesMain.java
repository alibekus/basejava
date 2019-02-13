import java.io.File;

class ListFilesMain {
    private static void printDirectoryContent(File file, String tab) {
        if (!file.isDirectory()) {
            System.out.println(tab + "-" + file.getName());
        }
        if (file.isDirectory()) {
            System.out.println(tab + "|");
            File[] structure = file.listFiles();
            if (structure != null) {
                for (File unit : structure) {
                    if (!unit.getName().equals(".git")) {
                        printDirectoryContent(unit, tab + "\t");
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        File file = new File(".");
        System.out.println(file.getAbsolutePath());
        printDirectoryContent(file, "\t");
    }
}
