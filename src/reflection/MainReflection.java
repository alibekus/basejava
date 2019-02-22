package reflection;

import model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class MainReflection {

    public static void main(String[] args) {
        Resume r = new Resume("uuid","FullName");
        System.out.println("getting first declared field...");
        Field field = r.getClass().getDeclaredFields()[0];
        System.out.println("setting accessible true to the field");
        field.setAccessible(true);
        System.out.println("name of the field: " + field.getName());
        try {
            System.out.println("value of the field: " + field.get(r));
            System.out.println("setting new value of the field...");
            field.set(r, "new_uuid");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        // invoke r.toString via reflection
//        System.out.println(r);
        try {
            System.out.println("getting method toString()...");
            Method toStringMethod = r.getClass().getMethod("toString");
            System.out.println("toStringMethod invoking: " + toStringMethod.invoke(r));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
