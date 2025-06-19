
package xyz;

import java.util.TreeSet;
import java.util.NavigableSet;

public class TreeSetNames {

	public static void main(String[] args) {
		TreeSet<String> name = new TreeSet<String>();
		name.add("sumit");
		name.add("karan");
		name.add("Anjali");
		name.add("Mohan");
		name.add("Zoya");
		System.out.println(name);
		NavigableSet<String> reversedSet = name.descendingSet();
        System.out.println(reversedSet);

	}

}
