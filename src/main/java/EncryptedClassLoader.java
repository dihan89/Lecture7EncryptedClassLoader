import java.io.*;
import java.util.*;

public class EncryptedClassLoader extends ClassLoader{
    private final int key;
    private final File dir;
    public final static int MODULE = 256;
    private int getIntKey(String key) throws NumberFormatException{
        return Integer.parseInt(key) % MODULE;
    }

    /**
     *
     * @param key must be String record for integer
     * @param dir is directory for classes
     * @param parent is parent classloader
     */
    public EncryptedClassLoader(String key, File dir, ClassLoader parent) {
        super(parent);
        int temp = 0;
        this.key = getIntKey(key);
        this.dir= dir;
    }

    public List<Class<?>> EncryptAndLoad() throws FileNotFoundException, ClassNotFoundException {
        if (!dir.exists()|| !dir.isDirectory()){
            throw new FileNotFoundException("Directory " + dir.getAbsolutePath()+ " is not found!");
        }
        List<Class<?>> listClasses = new ArrayList<>();
        for (File file : dir.listFiles())
            if (file.isFile() && file.getName().endsWith(".class")){
                try {
                    listClasses.add(findClass(file.getCanonicalPath()));
                } catch(IOException exc){
                    System.out.println("very bad:(   " + exc.getMessage());
                }
            }
        return listClasses;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File fileClass = new File(name);
        try (FileInputStream fis = new FileInputStream(fileClass)){
            byte[] clazzByteSequence = new byte[fis.available()];
            int curInd = 0;
            while (true){
                int current = fis.read();
                if(current == -1)
                    break;;
                clazzByteSequence[curInd++] = (byte) (current - key);
            }
           return defineClass(fileClass.getName().replace(".class",""), clazzByteSequence, 0, clazzByteSequence.length);
        } catch (IOException  exc){
            throw new ClassNotFoundException("File not found" + exc.getStackTrace().toString());
        }
    }

}
