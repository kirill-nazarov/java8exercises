public class Concatenation{

     public static void main(String []args){
        String s1 = "HELLO"; 
        String s2 = "HELLO";
        String arr[] = {"H","E","L","L","O"};
        String s3 = "";
        String s4 = "H"+ "E"+ "L"+ "L"+"O"; //compile time concatenation
        for(String var: arr){
            s3 = s3.concat(var); //concatenation at run time 
        }
        System.out.println(s1 == s3); //runtime concatenation => false
        System.out.println(s1.equals(s3));
        
        System.out.println("");
        
        System.out.println(s1 == s4); //compile time concatenation => true
        System.out.println(s1.equals(s4));
     }
}
