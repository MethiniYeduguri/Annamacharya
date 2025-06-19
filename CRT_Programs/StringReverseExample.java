
package uma;

public class StringReverseExample {

    public static void main(String[] args) {
        String sr = "bablu";
        String reversed = "";

        for (int i = sr.length() - 1; i >= 0; i--) {
            reversed += sr.charAt(i);  
        }

        System.out.println(reversed);
    }
}
