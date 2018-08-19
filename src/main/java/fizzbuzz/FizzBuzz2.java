package fizzbuzz;

/**
 * @author knazarov
 */
public class FizzBuzz2 {

    public static void main(String[] args) {
        for (int i = 1; i < 101; i++) {
            System.out.println(getResult(i));
        }

    }

    private static String getResult(int number) {
        String result = null;

        if (number % 15 == 0) {
            result = "FizzBuzz";
        } else if (number % 3 == 0) {
            result = "Fizz";
        } else if (number % 5 == 0) {
            result = "Buzz";
        } else {
            result = Integer.toString(number);
        }
        return result;
    }
}
