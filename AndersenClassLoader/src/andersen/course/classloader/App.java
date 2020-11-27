package andersen.course.classloader;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;

public class App {

    public static void main(String[] args) throws URISyntaxException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException {
        File jarFile = new File(App.class.getResource("/test.jar").toURI());
        MyClassLoader classLoader = new MyClassLoader(jarFile);
        try {
            Class<?> clazz = classLoader.findClass("andersen.course.classloader.test.Test");
            Object testObj = clazz.newInstance();
            clazz.getMethod("hello").invoke(testObj);
        } catch (ClassNotFoundException e) {
            System.err.println("Класс не найден");
        } catch (InstantiationException | IllegalAccessException e) {
            System.err.println("Ошибка создания объекта класса");
        }
    }
}
