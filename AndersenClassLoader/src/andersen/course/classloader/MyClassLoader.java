package andersen.course.classloader;

import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class MyClassLoader extends ClassLoader {


    /**
     * Кэш классов из jar-файла
     */
    private Map<String, Class<?>> classesCache = new HashMap<>();

    public MyClassLoader() {
        super(ClassLoader.getSystemClassLoader());
    }

    public MyClassLoader(File aJarFilePath) {
        super(ClassLoader.getSystemClassLoader());
        extractClasses(aJarFilePath);
    }

    @Override
    public Class<?> findClass(String className) throws ClassNotFoundException {
        Class<?> clazz = classesCache.get(className);
        if (clazz == null) {
            byte[] bt;
            try {
                bt = getClassBytes(getClass()
                        .getResourceAsStream("/" + className.replace(".", "/") + ".class"));
                if (bt != null) {
                    clazz = defineClass(className, bt, 0, bt.length);
                }
            } catch (FileNotFoundException ex) {
                return super.findClass(className);
            } catch (IOException e) {
                return super.findClass(className);
            }
        }

        return clazz;
    }

    /**
     * Возвращает класс из файла в файловой системе.
     *
     * @param classFile файл класса
     * @return класс из файла в файловой системе
     */
    public Class<?> findClass(File classFile) {
        try {
            byte[] bt = getClassBytes(new FileInputStream(classFile));
            if (bt != null) {
                return defineClass(classFile.getName(), bt, 0, bt.length);
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Файл класса не найден");
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла");
        }
        return Object.class;
    }

    /**
     * Извлекает классы из jar-файла.
     *
     * @param aJarFilePath путь Jar-файла
     */
    private void extractClasses(File aJarFilePath) {
        try (JarFile jarFile = new JarFile(aJarFilePath.getAbsolutePath());) {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.getName().endsWith(".class")) {
                    try (InputStream inputStream = jarFile.getInputStream(entry)) {
                        String className =
                                entry.getName().substring(0, entry.getName().length() - 6);
                        className = className.replace('/', '.');
                        byte[] classBytes = getClassBytes(inputStream);
                        if (classBytes != null) {
                            classesCache.put(className,
                                    defineClass(className, classBytes, 0, classBytes.length));
                        }
                    } catch (IOException ioException) {
                        System.err.println("Не удаелось получить класс " + entry.getName());
                    }
                }

            }
        } catch (IOException e) {
            System.err.println("Не удалось извлечь данные из jar-файла");
        }

    }

    /**
     * Возвращает данные класса в виде массива байт.
     *
     * @param is поток ввода
     * @return данные класса в виде массива байт
     * @throws IOException при ошибке ввода
     */
    private byte[] getClassBytes(InputStream is) throws IOException {
        if (is == null) {
            return null;
        }
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[0xFFFF];
            for (int len; (len = is.read(buffer)) != -1; ) {
                os.write(buffer, 0, len);
            }
            os.flush();
            return os.toByteArray();
        }
    }
}


