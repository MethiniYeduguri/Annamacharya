
package xyz;

import java.util.PriorityQueue;
import java.util.Collections;

public class HeapExample {

    public static void main(String[] args) {

        PriorityQueue<Integer> minheap = new PriorityQueue<>();
        minheap.add(5);
        minheap.add(15);
        minheap.add(20);

        System.out.println("Min heap output:");
        while (!minheap.isEmpty()) {
            System.out.println(minheap.poll());
        }

        // Corrected max heap initialization
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        maxHeap.add(20);
        maxHeap.add(15);
        maxHeap.add(5);

        System.out.println("Max heap output:");
        while (!maxHeap.isEmpty()) {
            System.out.println(maxHeap.poll());
        }
    }
}
