
package xyz;
import java.util.Stack;
public class StackUndoFeature {

	public static void main(String[] args) {
		Stack<String> su = new Stack<String>();
		su.push("write");
		su.push("Delete");
		su.push("Insert");
		System.out.println(su);
		System.out.println(su.peek());
		System.out.println(su.pop());
		System.out.println(su.pop());
		System.out.println(su);
		
		

	}

}
