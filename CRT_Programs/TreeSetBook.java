
package xyz;

import java.util.TreeSet;

public class TreeSetBook {

	public static void main(String[] args) {
		TreeSet<Integer> num = new TreeSet<Integer>();
		num.add(5002);
		num.add(4998);
		num.add(5005);
		num.add(4999);
		num.add(5001);
		System.out.println(num);
		System.out.println(num.first());
		System.out.println(num.last());
		System.out.println(num.higher(4999));
		System.out.println(num.lower(5005));
		


	}

}
