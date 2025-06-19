
package xyz;

import java.util.*;

public class SocialCircleDFS {
    Map<String, List<String>> graph = new HashMap<>();

    public void addConnection(String person1, String person2) {
        graph.computeIfAbsent(person1, k -> new ArrayList<>()).add(person2);
        graph.computeIfAbsent(person2, k -> new ArrayList<>()).add(person1);
    }

    public List<String> findSocialCircle(String person) {
        Set<String> visited = new HashSet<>();
        List<String> result = new ArrayList<>();
        dfsRecursive(person, visited, result);
        return result;
    }

    private void dfsRecursive(String person, Set<String> visited, List<String> result) {
        if (visited.contains(person)) return;
        visited.add(person);
        result.add(person);
        for (String friend : graph.getOrDefault(person, new ArrayList<>())) {
            dfsRecursive(friend, visited, result);
        }
    }

    public List<String> findSocialCircleIterative(String person) {
        Set<String> visited = new HashSet<>();
        List<String> result = new ArrayList<>();
        Stack<String> stack = new Stack<>();
        stack.push(person);

        while (!stack.isEmpty()) {
            String current = stack.pop();
            if (!visited.contains(current)) {
                visited.add(current);
                result.add(current);
                for (String friend : graph.getOrDefault(current, new ArrayList<>())) {
                    if (!visited.contains(friend)) {
                        stack.push(friend);
                    }
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        SocialCircleDFS sc = new SocialCircleDFS();

        sc.addConnection("Alice", "Bob");
        sc.addConnection("Alice", "Charlie");
        sc.addConnection("Bob", "David");
        sc.addConnection("Eve", "Frank");

        System.out.println("Recursive DFS (Social Circle of Alice):");
        List<String> circleRecursive = sc.findSocialCircle("Alice");
        System.out.println(circleRecursive);

        System.out.println("\nIterative DFS (Social Circle of Alice):");
        List<String> circleIterative = sc.findSocialCircleIterative("Alice");
        System.out.println(circleIterative);

        System.out.println("\nRecursive DFS (Social Circle of Eve):");
        List<String> circleEve = sc.findSocialCircle("Eve");
        System.out.println(circleEve);
    }
}
