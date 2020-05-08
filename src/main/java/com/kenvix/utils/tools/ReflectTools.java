package com.kenvix.utils.tools;

import com.kenvix.utils.annotation.Description;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.NoSuchElementException;

public final class ReflectTools {
    private ReflectTools() {}

    public static String getMethodDescription(Method method) {
        if(!method.isAnnotationPresent(Description.class))
            throw new NoSuchElementException("No Description found.");
        return method.getAnnotation(Description.class).message();
    }

    public static String getMethodDescription(String className, String methodName) throws ClassNotFoundException, NoSuchMethodException {
        Method targetMethod = ReflectTools.class.getClassLoader().loadClass(className).getMethod(methodName);
        return getMethodDescription(targetMethod);
    }

    public static String getMethodDescription() throws ClassNotFoundException, NoSuchMethodException {
        StackTraceElement[] stackTraceElement = Thread.currentThread().getStackTrace();
        StackTraceElement element = stackTraceElement[stackTraceElement.length-1];
        return getMethodDescription(element.getClassName(), element.getMethodName());
    }

    public static StackTraceElement getInvoker() {
        return new Throwable().getStackTrace()[1];
    }

    /**
     * FORCE Replace field.
     * If a field is not accessible or final, it will be updated to non-final and public
     *
     * @param target
     * @param fieldName
     * @param newObject
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static void replaceField(@NotNull Object target, @NotNull String fieldName, Object newObject)
            throws NoSuchFieldException, IllegalAccessException {
        Field field;

        if (target instanceof Class)
            field = ((Class) target).getDeclaredField(fieldName);
        else
            field = target.getClass().getDeclaredField(fieldName);

        Field modifiersField = Field.class.getDeclaredField("modifiers");

        AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
            field.setAccessible(true);
            modifiersField.setAccessible(true);
            return null;
        });

        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        if ((field.getModifiers() & Modifier.STATIC) > 0)
            field.set(null, newObject);
        else
            field.set(target, newObject);
    }
}
