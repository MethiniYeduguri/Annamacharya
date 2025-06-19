
package xyz;

import java.util.TreeSet;
import java.util.Scanner;

public class TreeSetProductNames {

	public static void main(String[] args) {
		TreeSet<String> pn = new TreeSet<String>();
		pn.add("Keyboard");
		pn.add("Mouse");
		pn.add("Monitor");
		pn.add("CPU");
		pn.add("Webcam");
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the product name:");
		String name = sc.nextLine();
		if(pn.contains(name)) {
			System.out.println("Product exists");
		}
		else {
			System.out.println("Product not exsits");
		}
		sc.close();
		

	}

}
