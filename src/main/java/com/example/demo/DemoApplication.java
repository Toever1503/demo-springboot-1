package com.example.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

import static java.lang.reflect.Modifier.*;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) throws IllegalAccessException {
//		SpringApplication.run(DemoApplication.class, args);

        UserTest userTest = new UserTest(1L, "test");
        UserTest userTest1 = new UserTest(2L, "test2");


        Field field = userTest.getClass().getSuperclass().getDeclaredFields()[0];
        field.setAccessible(true);

        IInterface annotation = (IInterface) getAnnotation(IInterface.class.getName(), field.getDeclaredAnnotations());

        System.out.println(annotation.value());
    }


    /*
    field's   modifier value:
    public field modifier = 1
    private field modifier = 2
    public static field modifier = 9
    private static field modifier = 10
     */
    public void compareObject(Object originalInstance, Object newInstance) throws IllegalAccessException {
        List<Field> originalInsFsList = null;

        if (!originalInstance.getClass().getSuperclass().getName().equals(Object.class.getName())) { // check if the class has super class
            originalInsFsList = (List<Field>) combineMultipleArrays(originalInstance.getClass().getDeclaredFields());
        }


        if (originalInstance.getClass().getName().equals(newInstance.getClass().getName())
                || originalInsFsList.size() == 0)
            System.out.println("Object is not equal");
        int fieldLength = originalInsFsList.size();

        int changedCount = 0;
        StringBuilder checkFileChange = new StringBuilder("đã thay cập nhật file đính kèm!<br>");

        for (int i = 0; i < fieldLength; ++i) {
            Field field = originalInsFsList.get(i);
            // IGNORE STATIC FIELDS
            if (field.getModifiers() != PUBLIC || field.getModifiers() != PUBLIC)
                continue;

            // IGNORE WHEN CHANGE GT 1
            if (changedCount > 1)
                break;

            String className = field.getType().getName();

            if (field.getModifiers() == PUBLIC) {
                if (className.equals("ss")) {

                    StringBuilder aHtmlTag = new StringBuilder("<a href=\"\" download>");
                    aHtmlTag.append("<a download href='"); // begin a tag
                    aHtmlTag.append("file url"); // add url at here
                    aHtmlTag.append("'>");

                    aHtmlTag.append("file name"); // add file name at here
                    aHtmlTag.append("</a><br>"); // end a tag
                    checkFileChange.append(aHtmlTag); // append file link

                } else if (field.get(originalInstance) != field.get(newInstance))
                    changedCount++;

            } else {
                field.setAccessible(true);


                if (className.equals("ss")) {

                } else if (field.get(originalInstance) != field.get(newInstance))
                    changedCount++;

                field.setAccessible(false);
            }
        }

    }

    public static Annotation getAnnotation(String className, Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().getName().equals(className))
                return annotation;
        }
        return null;
    }

    public static Collection<?> combineMultipleArrays(Object[]... arrays) {
        Collection<Object> result = new ArrayList<>();
        for (Object[] array : arrays) {
            for (Object o : array) {
                result.add(o);
            }
        }
        return result;
    }
}
