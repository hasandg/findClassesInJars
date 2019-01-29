import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * Created by : hdag
 * Date: 18.11.2013
 * Time: 09:33
 */
public class findClassesInJarsMain {

    private static List<File> jarFiles = new ArrayList<File>();

    public static void main(String[] args) throws IOException {
        List<File> files = listJarFilesForFolder(new File("M:\\prj\\bm\\modules\\helpers\\build\\depend\\helpers"));
        FileWriter fileWriter = new FileWriter(new File("D:\\out.txt"),true);
        BufferedWriter out = new BufferedWriter(fileWriter);
        for (File file : files) {
            try {

                out.write("\n" + "file : " + file.getAbsolutePath() + "\n\n");
                out.flush();
                getClasses(file.getAbsolutePath(), out);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        out.close();
    }

    public static List<File> listJarFilesForFolder(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listJarFilesForFolder(fileEntry);
            } else if (fileEntry.getName().endsWith(".jar")) {
                jarFiles.add(fileEntry);
            }
        }
        return jarFiles;
    }

    public static ArrayList<Class> getClasses(String jarName, BufferedWriter out)
            throws ClassNotFoundException {
        ArrayList<Class> classes = new ArrayList<Class>();


        System.out.println(jarName + " processing...");

        File f = new File(jarName);
        if (f.exists()) {
            try {
                JarInputStream jarFile = new JarInputStream(
                        new FileInputStream(jarName));
                JarEntry jarEntry;

                while (true) {
                    jarEntry = jarFile.getNextJarEntry();
                    if (jarEntry == null) {
                        break;
                    }
                    if ((jarEntry.getName().endsWith(".class"))) {

                   /*        classes.add(Class.forName(jarEntry.getName().
                                   replaceAll("/", "\\.").
                                   substring(0, jarEntry.getName().length() - 6), false, ClassLoader.getSystemClassLoader()));*/
                        out.write("Class Found: " + jarEntry.getName() + "\n");
                        out.flush();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return classes;
        } else
            return null;
    }
}
