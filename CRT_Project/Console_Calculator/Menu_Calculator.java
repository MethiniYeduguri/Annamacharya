package Calculator1;
import java.util.Scanner;

public class Menu_Calculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean keepRunning = true;

        while (keepRunning) {
            System.out.println("\n=== Calculator Menu ===");
            System.out.println("1. Use ArrayList");
            System.out.println("2. Use LinkedList");
            System.out.println("3. Use Queue");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> Calc_ArrayList.run();
                case 2 -> Calc_LinkedList.run();
                case 3 -> Calc_CircularQueue.run();
                case 4 -> {
                    keepRunning = false;
                    System.out.println("Exiting... Goodbye!");
                    continue; // skip asking for continuation
                }
                default -> {
                    System.out.println("Invalid choice.");
                    continue;
                }
            }
        }
    }
}
