package kz.knazarov.oca;

public class Loop {

    public static void main(String[] args) {
        int ctr =10;
        char[] arrC1 = new char[] {'P','a','u','l'};
        char[] arrC2 = new char[] {'H','a','r','r','y'};

        for (char c1: arrC1)
            for (char c2: arrC2)
                if (c2 == 'a') break;
                ++ctr;

        System.out.println(ctr);
    }
}
