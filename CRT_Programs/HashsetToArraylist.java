package xyz;

import java.util.HashSet;
import java.util.ArrayList;
public class HashsetToArraylist{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashSet<String> u = new HashSet<String>();
		u.add("bablu");
		u.add("uma");
		u.add("geethu");
		u.add("anamcara");
		u.add("anu");
		u.add("janu");
		ArrayList<String> uu = new ArrayList<String>(u);
		System.out.println(uu);
		

	}

}
