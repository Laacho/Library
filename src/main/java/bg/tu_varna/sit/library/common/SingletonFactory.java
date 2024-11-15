package bg.tu_varna.sit.library.common;

import bg.tu_varna.sit.library.common.annotations.Processor;
import bg.tu_varna.sit.library.common.annotations.Singleton;
import org.jetbrains.annotations.Nullable;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SingletonFactory {
    private static Map<Class<?>, Object> singletonInstances = new HashMap<>();


    public static <T> T getSingletonInstance(Class<T> clazz) {
        if (singletonInstances.containsKey(clazz)) {
            return (T) singletonInstances.get(clazz);
        }
        throw new RuntimeException();
    }

    @Nullable
    public static <T> void init() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Reflections reflections = new Reflections("bg.tu_varna.sit.library");
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(Singleton.class);
        makeInstance(typesAnnotatedWith);
        Set<Class<?>> annotatedWithProcessor = reflections.getTypesAnnotatedWith(Processor.class);
        makeInstance(annotatedWithProcessor);
    }

    private static <T> void makeInstance(Set<Class<?>> typesAnnotatedWith) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        for (Class<?> singletonClass : typesAnnotatedWith) {
            Constructor<?> constructor = singletonClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            T o = (T) constructor.newInstance();
            constructor.setAccessible(false);
            singletonInstances.put(singletonClass, o);
        }
    }

}