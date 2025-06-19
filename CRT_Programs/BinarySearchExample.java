
package uma;

public class BinarySearchExample {

	public static void main(String[] args) {
		int[] numbers = {2,4,5,7,8,9};
		int target = 8;
		int left = 0;
		int right=numbers.length-1;
		boolean found = false;
		while(left <= right) {
			int mid = (left+right)/2;
			if(numbers[mid]== target) {
				System.out.println("Found"+" "+target+" at index" + " "+mid);
				found = true;
				break;
			}else if(target<numbers[mid]) {
				right=mid-1;
			}else {
				left = mid+1;
				
			}
		}
		if(!found) {
			System.out.println("Number not found");
		}

	}

}
