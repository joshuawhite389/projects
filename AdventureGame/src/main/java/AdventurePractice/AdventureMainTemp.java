package AdventurePractice;

import java.util.Scanner;

public class AdventureMainTemp 
{
 
    public static void main(String[] args) throws Exception 
     {
        Scanner in = new Scanner(System.in);
           
        System.out.println("Enter a string: ");
          String s = in.nextLine();
           
        while(true){
            System.out.print("\r" + s);
            Thread.sleep(500);
           // s = s.substring(1) + s.substring(0,1);
        }
    }
}