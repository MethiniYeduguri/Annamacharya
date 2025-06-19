
package xyz;
import java.util.*;
public class binarysearchsquareroot {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
        System.out.print("Enter a number: ");
        int n = sc.nextInt();
        
        int low = 0, high = n, ans = 0;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (mid * mid == n) {
                ans = mid;
                break;
            } else if (mid * mid < n) {
                ans = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        
        System.out.println("Square root: " + ans);
        sc.close();

		

	}

}
