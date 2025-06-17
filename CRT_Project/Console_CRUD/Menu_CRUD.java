package CRUD;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Menu_CRUD 
{

    private static final String DB_URL = "jdbc:mysql://localhost:3306/cse";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "3030";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("========= SQL File Executor =========");
        System.out.println("1. CREATE TABLE commands");
        System.out.println("2. INSERT DEPT rows");
        System.out.println("3. INSERT EMP rows");
        System.out.println("4. INSERT SALGRADE rows");
        System.out.println("5. DELETE FROM DEPT");
        System.out.println("6. DELETE FROM EMP");
        System.out.println("7. DELETE FROM SALGRADE");
        System.out.print("Enter your choice (1-7): ");
        int choice = scanner.nextInt();

        String filename = switch (choice) {
            case 1 -> "E:/CRT/CRUD/createtables.sql";
            case 2 -> "E:/CRT/CRUD/insDept.txt";
            case 3 -> "E:/CRT/CRUD/insEmp.txt";
            case 4 -> "E:/CRT/CRUD/insSalgrade.txt";
            case 5 -> "E:/CRT/CRUD/delDept.sql";
            case 6 -> "E:/CRT/CRUD/delEmp.sql";
            case 7 -> "E:/CRT/CRUD/delSalgrade.sql";
            default -> null;
        };

        if (filename != null) {
            runSQLFromFile(filename);
        } else {
            System.out.println("Invalid choice.");
        }

        scanner.close();
    }

    public static void runSQLFromFile(String filename) 
    {
        try 
        {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                Statement statement = connection.createStatement())
            {

                String sql = Files.readString(Paths.get(filename));
                String[] queries = sql.split(";");

                for (String query : queries) 
                {
                    if (!query.trim().isEmpty()) 
                    {
                        boolean result = statement.execute(query.trim());
                        System.out.println("Executed: " + query.trim());
                    }
                }
                System.out.println("\nAll SQL commands in " + filename + " executed successfully.\n");
            }
            catch (SQLException | IOException e)
            {
                System.out.println("\nError executing SQL from file: " + filename);
                System.out.println("Reason: " + e.getMessage());
            }
        } 
        catch (ClassNotFoundException e) 
        {
            System.out.println("MySQL JDBC Driver not found.");
        }
    }
}