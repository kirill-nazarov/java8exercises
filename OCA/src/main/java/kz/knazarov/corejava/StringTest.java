package kz.knazarov.corejava;

public class StringTest {
    public static void main(String[] args) {
        StringBuilder b1 = new StringBuilder("snorkler");
        StringBuilder b2 = new StringBuilder("yoodler");

    testOne(new StringBuilder("snorkler"),new StringBuilder("yoodler"));
    testTwo(new StringBuilder("snorkler"),new StringBuilder("yoodler"));
    testThree(new StringBuilder("snorkler"),new StringBuilder("yoodler"));


    }

    static void testOne(StringBuilder b1, StringBuilder b2){
        b1.append(b2.substring(2,5).toUpperCase());
        System.out.println(b1);
        System.out.println(b2);
    }

    static void testTwo(StringBuilder b1, StringBuilder b2){
        b2.insert(3, b1.append("a"));
        System.out.println(b1);
        System.out.println(b2);
    }

    static void testThree(StringBuilder b1, StringBuilder b2){
        b1.replace(3,4,b2.substring(4)).append(b2.append(false));
        System.out.println(b1);
        System.out.println(b2);
    }
}
