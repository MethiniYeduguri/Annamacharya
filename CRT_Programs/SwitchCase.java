
package xyz;

import java.util.Scanner;

public class SwitchCase {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a day of the week:");
        String day = sc.nextLine().toLowerCase();

        switch(day) {
            case "monday":
                System.out.println("Let's learn Python");
                break;
            case "tuesday":
                System.out.println("Let's learn Java");
                break;
            case "wednesday":
                System.out.println("Let's learn Angular JS");
                break;
            case "thursday":  
                System.out.println("Let's learn Swift");
                break;
            case "friday":
                System.out.println("Let's learn Ruby");
                break;
            case "saturday":
                System.out.println("Let's learn C++");
                break;
            case "sunday":
                System.out.println("Let's learn CSA");
                break;
            default:
                System.out.println("Invalid day entered!");
                break;
        }
        
        sc.close();
    }
}
