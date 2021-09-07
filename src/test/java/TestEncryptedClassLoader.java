import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class TestEncryptedClassLoader {
    private File outdir;

    @BeforeEach
    public void beforeMethod() throws Exception{
        Lecture7EncryptedClassLoader.cryptDir("D:/JavaTasksSber/Lecture7EncryptedClassLoader/src/classesBeforeCrypt/",
                "D:/JavaTasksSber/Lecture7EncryptedClassLoader/src/ClassesAfterCrypt/", "573");
        outdir = new File("D:/JavaTasksSber/Lecture7EncryptedClassLoader/src/ClassesAfterCrypt/");
    }

    @Test
    public void  encodeReturnTrue() throws FileNotFoundException, ClassNotFoundException {
            EncryptedClassLoader encLoader = new EncryptedClassLoader("573", outdir, ClassLoader.getSystemClassLoader());
            java.util.List<Class<?>> listClasses = encLoader.EncryptAndLoad();
            for (Class<?> clazz : listClasses) {
                System.out.println(clazz.getName());
                Assertions.assertEquals(10, listClasses.size());
        }
    }

    @Test
    public void  encodeExceptionDifferentKeys()  {
        Assertions.assertThrows( ClassFormatError.class, ()->{
            EncryptedClassLoader encLoader = new EncryptedClassLoader("543", outdir, ClassLoader.getSystemClassLoader());
            java.util.List<Class<?>> listClasses = encLoader.EncryptAndLoad();
            for (Class<?> clazz : listClasses) {
                System.out.println(clazz.getName());
            }
        });
    }

    @Test
    public void createObjectByLoadClassReturnTrueForSamePasswords()
            throws InvocationTargetException, InstantiationException,
                IllegalAccessException, NoSuchMethodException, FileNotFoundException, ClassNotFoundException {
        EncryptedClassLoader encLoader = new EncryptedClassLoader("573", outdir, ClassLoader.getSystemClassLoader());
        java.util.List<Class<?>> listClasses = encLoader.EncryptAndLoad();
        for (Class<?> clazz : listClasses) {
            if(clazz.getName().equals("TerminalServer")){
                Constructor<?> constructor =  clazz.getDeclaredConstructor(new Class<?>[]{int[].class});
                constructor.setAccessible(true);
                var zTerminal = constructor.newInstance(new int[]{1,2,2,3});
                constructor.setAccessible(false);
                Method method = clazz.getDeclaredMethod("checkPassword", new Class<?>[]{int[].class});
                method.setAccessible(true);
                Assertions.assertTrue((Boolean) method.invoke(zTerminal, new int[]{1,2,2,3}));
                method.setAccessible(false);
            }
        }
    }

    @Test
    public void createObjectByLoadClassReturnFalseForDifferentPasswords()
            throws InvocationTargetException, InstantiationException,
            IllegalAccessException, NoSuchMethodException, FileNotFoundException, ClassNotFoundException {
        EncryptedClassLoader encLoader = new EncryptedClassLoader("573", outdir, ClassLoader.getSystemClassLoader());
        java.util.List<Class<?>> listClasses = encLoader.EncryptAndLoad();
        for (Class<?> clazz : listClasses) {
            if(clazz.getName().equals("TerminalServer")){
                Constructor<?> constructor =  clazz.getDeclaredConstructor(new Class<?>[]{int[].class});
                constructor.setAccessible(true);
                var zTerminal = constructor.newInstance(new int[]{1,2,2,3});
                constructor.setAccessible(false);
                Method method = clazz.getDeclaredMethod("checkPassword", new Class<?>[]{int[].class});
                method.setAccessible(true);
                Assertions.assertFalse((Boolean) method.invoke(zTerminal, new int[]{1,2,5,3}));
                method.setAccessible(false);
            }
        }
    }

    @Test
    public void  encodeReturnTrueNullParent() throws FileNotFoundException, ClassNotFoundException {
        EncryptedClassLoader encLoader = new EncryptedClassLoader("573", outdir, null);
        java.util.List<Class<?>> listClasses = encLoader.EncryptAndLoad();
        for (Class<?> clazz : listClasses) {
            System.out.println(clazz.getName());
            Assertions.assertEquals(10, listClasses.size());
        }
    }

    @Test
    public void  encodeExceptionDifferentKeysNullParent()  {
        Assertions.assertThrows( ClassFormatError.class, ()->{
            EncryptedClassLoader encLoader = new EncryptedClassLoader("543", outdir, null);
            java.util.List<Class<?>> listClasses = encLoader.EncryptAndLoad();
            for (Class<?> clazz : listClasses) {
                System.out.println(clazz.getName());
            }
        });
    }

    @Test
    public void createObjectByLoadClassReturnTrueForSamePasswordsNullParent()
            throws InvocationTargetException, InstantiationException,
            IllegalAccessException, NoSuchMethodException, FileNotFoundException, ClassNotFoundException {
        EncryptedClassLoader encLoader = new EncryptedClassLoader("573", outdir, null);
        java.util.List<Class<?>> listClasses = encLoader.EncryptAndLoad();
        for (Class<?> clazz : listClasses) {
            if(clazz.getName().equals("TerminalServer")){
                Constructor<?> constructor =  clazz.getDeclaredConstructor(new Class<?>[]{int[].class});
                constructor.setAccessible(true);
                var zTerminal = constructor.newInstance(new int[]{1,2,2,3});
                constructor.setAccessible(false);
                Method method = clazz.getDeclaredMethod("checkPassword", new Class<?>[]{int[].class});
                method.setAccessible(true);
                Assertions.assertTrue((Boolean) method.invoke(zTerminal, new int[]{1,2,2,3}));
                method.setAccessible(false);
            }
        }
    }

    @Test
    public void createObjectByLoadClassReturnFalseForDifferentPasswordsNullParent()
            throws InvocationTargetException, InstantiationException,
            IllegalAccessException, NoSuchMethodException, FileNotFoundException, ClassNotFoundException {
        EncryptedClassLoader encLoader = new EncryptedClassLoader("573", outdir, null);
        java.util.List<Class<?>> listClasses = encLoader.EncryptAndLoad();
        for (Class<?> clazz : listClasses) {
            if(clazz.getName().equals("TerminalServer")){
                Constructor<?> constructor =  clazz.getDeclaredConstructor(new Class<?>[]{int[].class});
                constructor.setAccessible(true);
                var zTerminal = constructor.newInstance(new int[]{1,2,2,3});
                constructor.setAccessible(false);
                Method method = clazz.getDeclaredMethod("checkPassword", new Class<?>[]{int[].class});
                method.setAccessible(true);
                Assertions.assertFalse((Boolean) method.invoke(zTerminal, new int[]{1,2,5,3}));
                method.setAccessible(false);
            }
        }
    }
}
