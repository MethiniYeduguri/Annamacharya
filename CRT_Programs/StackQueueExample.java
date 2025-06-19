
package uma;
import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;
public class StackQueueExample {
	public static void main(String[] args) {
		Stack<String> stack = new Stack<String>();
		Queue<String> queue = new LinkedList<String>();
		stack.push("Ball");
		stack.push("Blocks");
		stack.push("Puzzle");
		System.out.println(stack);
		System.out.println(stack.pop());
		queue.add("Maya");
		queue.add("Leo");
		queue.add("Nina");
		System.out.println("serve the first kid " + queue.peek());
		System.out.println(queue);
		queue.poll();
		System.out.println(queue);
	}

}
