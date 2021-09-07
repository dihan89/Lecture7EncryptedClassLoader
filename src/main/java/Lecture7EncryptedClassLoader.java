import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Lecture7EncryptedClassLoader {

    public Lecture7EncryptedClassLoader() throws FileNotFoundException, ClassNotFoundException {
    }

    public static void  cryptDir(String inputDirectoryPath, String outDirecoryPath, String keyStr)
            throws FileNotFoundException, NumberFormatException, ClassNotFoundException{
        File inputDirectory = new File(inputDirectoryPath);
        File outDirectory = new File(outDirecoryPath);
        int key = 0;
        if (!inputDirectory.exists() || !inputDirectory.isDirectory()){
            throw new FileNotFoundException("Input directory is not exist!");
        }
        if (!outDirectory.exists() || !outDirectory.isDirectory()){
            throw new FileNotFoundException("Output directory is not exist!");
        }
        try{
            key = Integer.parseInt(keyStr) % EncryptedClassLoader.MODULE;
        } catch(NumberFormatException exc){
            System.out.println("The key is not correct!");
            throw exc;
        }
        for (File file : inputDirectory.listFiles())
            if (file.isFile() && file.getName().endsWith(".class")){
                cryptClass(file, new File(outDirecoryPath, file.getName()), key);
            }
    }
    public static void cryptClass(File input, File output, int key) throws ClassNotFoundException {
        try (FileInputStream fis = new FileInputStream(input);
                FileOutputStream fos = new FileOutputStream(output)){
            byte[] clazzByteSequence = new byte[fis.available()];
            int curInd = 0;
            while (true){
                int current = fis.read();
                if(current == -1)
                    break;
                clazzByteSequence[curInd++] = (byte) (current + key);
            }
            fos.write(clazzByteSequence);
        } catch (FileNotFoundException exc){
            throw new ClassNotFoundException("File not found" + exc.getStackTrace().toString());
        } catch (IOException exc){

        }
    }
    public static void main(String[] args)  {

    }
}
