package uma;
import  java.util.*;
public class binarysearchnames {

	public static void main(String[] args) {
		String[] names = {"Amil", "Divya", "karan", "priya", "zoya"};
        Arrays.sort(names);
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a name to search: ");
        String input = sc.nextLine();

        if (Arrays.binarySearch(names, input) >= 0)
            System.out.println("Name found.");
        else
            System.out.println("Name not found.");
        
        sc.close();


	}

}
