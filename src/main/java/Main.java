import java.io.File;
import java.io.IOException;
import java.sql.SQLException;


public class Main {
    public static void main(String args[]) throws IOException, SQLException, ClassNotFoundException {
        String path = "/home/luckcul/downloads";
        path = args[0];
        int disk =  Integer.parseInt(args[1]);
//        int disk =  Integer.parseInt("9");
        File file = new File(path);
        File[] array = file.listFiles();
        SubThread subThread[] = new SubThread[array.length];

        for(int i = 0; i < array.length; i++) {
            System.out.println(array[i].getPath());
            subThread[i] = new SubThread(array[i].getPath(), disk);
        }
        for(int i = 0; i < subThread.length; i++) {
            subThread[i].start();
        }
//        Path dir = Paths.get(path);
//        FindFileVisitor finder = new FindFileVisitor();
//        Files.walkFileTree(dir, finder);
    }
}
