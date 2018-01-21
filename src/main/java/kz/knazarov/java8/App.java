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

        //example of Consumer interface object
        //Consumer c = (Object o) -> {System.out.println(">>>" + o);};
        //l.forEach(c);

        //example of forEach method on List
        //l.forEach((Object o)-> {System.out.println(">>>"+ o);});

        //the same compact version
        list.forEach(str->System.out.println(">>>"+ str));
    }
}
