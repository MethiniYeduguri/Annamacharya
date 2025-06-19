
package xyz;

import java.util.*;

public class DFSExample {

    private Map<String, List<String>> homeMap = new HashMap<>();
    private Set<String> visited = new HashSet<>();

    public void addRoom(String room, String connectedRoom) {
        homeMap.computeIfAbsent(room, k -> new ArrayList<>()).add(connectedRoom);
        homeMap.computeIfAbsent(connectedRoom, k -> new ArrayList<>()).add(room);
    }

    public void dfs(String currentRoom) {
        if (visited.contains(currentRoom)) return;

        visited.add(currentRoom);
        System.out.println("Visited: " + currentRoom);

        for (String neighbor : homeMap.getOrDefault(currentRoom, new ArrayList<>())) {
            dfs(neighbor);
        }
    }
    public static void main(String[] args) {
        DFSExample house = new DFSExample();

        house.addRoom("LivingRoom", "Kitchen");
        house.addRoom("Kitchen", "Bathroom");
        house.addRoom("LivingRoom", "Bedroom");

        System.out.println("DFS Traversal Starting from LivingRoom:");
        house.dfs("LivingRoom");
    }
  }
