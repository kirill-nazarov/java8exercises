package kz.knazarov.java8;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author knazarov
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        List list = Arrays.asList("Hello", "World", "My", "List");

        //example of Functional Interface Consumer object
        Consumer c = (Object o) -> {System.out.println(">>>" + o);};
        list.forEach(c);

        //example of forEach method on List no special object created explicitly
        list.forEach((Object o)-> {System.out.println(">>>"+ o);});


        //the same compact version
        list.forEach(str->System.out.println(">>>"+ str));
    }
}
