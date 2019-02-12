import java.io.File;

class ListDirectoryMain {
    private static void printDirectoryContent(File file, String tab) {
        System.out.println(tab + file.getName());
        if (file.isDirectory()) {
            File[] structure = file.listFiles();
            tab = tab + "\t";
            for (File unit : structure) {
                if (!unit.getName().equals(".git")) {
                    printDirectoryContent(unit, tab);
                }
            }
        }
    }

    public static void main(String[] args) {
        File file = new File("C:\\Users\\Alibek\\Documents\\IT\\Edu\\Java\\javaops\\basejava");
        System.out.println(file.getAbsolutePath());
        printDirectoryContent(file, "\t");
    }
}
