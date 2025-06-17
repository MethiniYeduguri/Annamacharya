public class Main {
    public static <T> void print(T[] array) {
        for (T item : array) {
            System.out.print(item + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Integer[] arrayint = {1, 2, 3, 4};
        String[] arraystr = {"A", "B", "C"};

        print(arrayint);
        print(arraystr);
    }
}
