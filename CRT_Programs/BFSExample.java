package abc;

import java.util.*;

public class BFSExample {

    public static void main(String[] args) {

        Map<String, List<String>> house = new HashMap<>();

        house.put("Living room", Arrays.asList("Kitchen", "Bedroom", "Study room"));
        house.put("Kitchen", Arrays.asList("Bathroom"));
        house.put("Bedroom", Arrays.asList("Balcony"));
        house.put("Study room", new ArrayList<>());
        house.put("Bathroom", new ArrayList<>());
        house.put("Balcony", new ArrayList<>());

        System.out.println("Exploring the house using BFS starting from the Living room:");
        bfs(house, "Living room");
    }

    public static void bfs(Map<String, List<String>> graph, String start) {
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            String currentRoom = queue.poll();
            System.out.println("Visited: " + currentRoom);

            for (String neighbor : graph.getOrDefault(currentRoom, new ArrayList<>())) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                }
            }
        }
    }
}
