class Show<T> {
    private T item;

    public void setItem(T item) {
        this.item = item;
    }

    public T getItem() {
        return item;
    }
}

public class Main {
    public static void main(String[] args) {
        Show<String> showstring = new Show<>();
        showstring.setItem("Hello Generics");
        System.out.println(showstring.getItem());

        Show<Integer> showint = new Show<>();
        showint.setItem(100);
        System.out.println(showint.getItem());
    }
}
