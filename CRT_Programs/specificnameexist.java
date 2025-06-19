
package xyz;

import java.util.ArrayList;

public class specificnameexist {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<String> names = new ArrayList<>();
        names.add("Alice");
        names.add("Bob");
        names.add("Charlie");

        if (names.contains("Bob")) {
            System.out.println("Name found!");
        } else {
            System.out.println("Name not found.");
        }

	}

}
