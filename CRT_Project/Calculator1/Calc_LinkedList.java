package Calculator1;
import java.util.*;

public class Calc_LinkedList {
    static List<Double> numbersEntered = new LinkedList<>(); // Track numbers using LinkedList

    public static void run() {
        Scanner scanner = new Scanner(System.in);
        boolean continueCalc = true;
        while (continueCalc) {
            System.out.print("Input expression: ");
            System.out.flush();
            String input = scanner.nextLine().trim();

            // ✅ Handle assignment-like inputs (e.g., x=4+5)
            if (input.contains("=")) {
                String[] parts = input.split("=");

                if (parts.length > 2) {
                    System.out.println("Invalid expression: multiple '=' found.");
                    continue;
                }

                if (parts.length == 2 && parts[0].trim().matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
                    System.out.println("Assignment not supported. Use just expressions.");
                    continue;
                }

                if (parts.length == 2 && !parts[0].matches(".*[+\\-*/()]") && !parts[0].trim().isEmpty()) {
                    System.out.println("Invalid expression format.");
                    continue;
                }

                input = parts[0] + (parts.length == 2 ? parts[1] : "");
            }

            // ✅ Validate for consecutive operators (e.g., 3++4)
            if (input.matches(".*[+\\-*/]{2,}.*")) {
                System.out.println("Invalid expression: consecutive operators found.");
                continue;
            }
            if (input.matches(".*[+\\-*/]$")) 
            {
                System.out.println("Invalid expression: ends with an operator.");
                continue;
            }

            try {
                numbersEntered.clear();
                List<String> postfix = infixToPostfix(input);
                double result = evaluatePostfix(postfix);

                System.out.println("Result: " + result);
                System.out.println("Numbers entered: " + numbersEntered);

                // ✅ Sort the LinkedList
                List<Double> sorted = new ArrayList<>(numbersEntered); // temporary sort in ArrayList
                Collections.sort(sorted);
                System.out.println("Sorted Numbers: " + sorted);

                // ✅ Remove duplicates
                Set<Double> unique = new LinkedHashSet<>(sorted);
                System.out.println("Sorted Numbers without duplicates: " + new ArrayList<>(unique));

                // ✅ Separate Even and Odd numbers
                List<Double> even = new LinkedList<>();
                List<Double> odd = new LinkedList<>();

                for (double d : unique) {
                    if (d % 2 == 0) {
                        even.add(d);
                    } else if (d % 2 != 0) {
                        odd.add(d);
                    }
                }

                System.out.println("Even Numbers: " + even);
                System.out.println("Odd Numbers: " + odd);
            } catch (Exception e) {
                System.out.println("Error in expression: " + e.getMessage());
            }
            System.out.print("Do you want to continue in LinkedList? (yes/no): ");
            String response = scanner.nextLine().trim().toLowerCase();
            if (!response.equals("yes") && !response.equals("y")) {
                continueCalc = false;
                System.out.println("Calculator session ended.");
            }
        }
    }

    // 🔄 Infix to Postfix (Shunting Yard)
    private static List<String> infixToPostfix(String expression) {
        List<String> output = new LinkedList<>();
        Stack<String> operators = new Stack<>();
        StringTokenizer tokens = new StringTokenizer(expression, "+-*/() ", true);

        while (tokens.hasMoreTokens()) {
            String token = tokens.nextToken().trim();
            if (token.isEmpty()) continue;

            if (isNumber(token)) {
                output.add(token);
            } else if (isOperator(token)) {
                while (!operators.isEmpty() && isOperator(operators.peek()) &&
                        precedence(token) <= precedence(operators.peek())) {
                    output.add(operators.pop());
                }
                operators.push(token);
            } else if (token.equals("(")) {
                operators.push(token);
            } else if (token.equals(")")) {
                while (!operators.isEmpty() && !operators.peek().equals("(")) {
                    output.add(operators.pop());
                }
                if (!operators.isEmpty() && operators.peek().equals("(")) {
                    operators.pop();
                } else {
                    throw new RuntimeException("Mismatched parentheses");
                }
            } else {
                throw new RuntimeException("Invalid token: " + token);
            }
        }

        while (!operators.isEmpty()) {
            String op = operators.pop();
            if (op.equals("(")) throw new RuntimeException("Mismatched parentheses");
            output.add(op);
        }

        return output;
    }

    // 💡 Evaluate Postfix
    private static double evaluatePostfix(List<String> postfix) {
        Stack<Double> stack = new Stack<>();
        for (String token : postfix) {
            if (isNumber(token)) {
                double num = Double.parseDouble(token);
                numbersEntered.add(num);
                stack.push(num);
            } else {
                double b = stack.pop();
                double a = stack.pop();
                switch (token) {
                    case "+" -> stack.push(a + b);
                    case "-" -> stack.push(a - b);
                    case "*" -> stack.push(a * b);
                    case "/" -> {
                        if (b == 0) throw new ArithmeticException("Division by zero");
                        stack.push(a / b);
                    }
                    default -> throw new RuntimeException("Unknown operator: " + token);
                }
            }
        }
        return stack.pop();
    }

    private static boolean isNumber(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isOperator(String s) {
        return "+-*/".contains(s);
    }

    private static int precedence(String op) {
        return switch (op) {
            case "+", "-" -> 1;
            case "*", "/" -> 2;
            default -> -1;
        };
    }
}
