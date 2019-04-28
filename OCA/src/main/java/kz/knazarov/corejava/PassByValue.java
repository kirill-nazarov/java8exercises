package kz.knazarov.corejava;

public class PassByValue {

    /*
    Java is always pass-by-value
    https://stackoverflow.com/questions/40480/is-java-pass-by-reference-or-pass-by-value
     */

    public static void main(String[] args) {
        Dog aDog = new Dog("Max");
        // we pass the object to foo
        foo(aDog);
        // aDog variable is still pointing to the "Max" dog when foo(...) returns
        boolean nameMax;
        boolean nameFifi;
        nameMax = aDog.getName().equals("Max"); // true
        nameFifi = aDog.getName().equals("Fifi"); // false
        System.out.println(nameMax);
        System.out.println(nameFifi);

    }

   // Java is always pass-by-value
    public static void foo(Dog d) {
        d.getName().equals("Max"); // true
        // change d inside of foo() to point to a new Dog instance "Fifi"
        d = new Dog("Fifi");
        d.getName().equals("Fifi"); // true
    }
}
