package fizzbuzz;

/**
 * @author knazarov
 */
public class FizzBuzz {

    public static void main(String[] args) {
        for (int i = 1; i < 101; i++) {
            System.out.println(getResult(i));
        }

    }

    private static String getResult(int number) {
        if (number % 15 == 0) return "FizzBuzz";
        if (number % 3 == 0) return "Fizz";
        if (number % 5 == 0) return "Buzz";
        return Integer.toString(number);
    }
}
