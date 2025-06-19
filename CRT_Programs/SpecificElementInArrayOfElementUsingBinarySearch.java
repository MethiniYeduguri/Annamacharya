
package abc;
import java.util.Scanner;
import java.util.Arrays;

public class SpecificElementInArrayOfElementUsingBinarySearch {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] array = {2, 5, 8, 12, 16};
        System.out.print("Enter the element to search: ");
        int key = scanner.nextInt();
        int result = binarySearch(array, key);
        if (result == -1) {
            System.out.println("Element not found in the array.");
        } else {
            System.out.println("Element found at index: " + result);
        }
        scanner.close();
    }

    static int binarySearch(int[] arr, int key) {
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == key)
                return mid;
            if (arr[mid] < key)
                left = mid + 1;
            else
                right = mid - 1;
        }
        return -1;
    }
}
