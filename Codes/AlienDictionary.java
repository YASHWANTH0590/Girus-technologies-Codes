/*
🔸 Problem: Alien Dictionary

Given a sorted list of words from an alien language, determine the character order used in that language.
 Approach:
- Build a graph where each node is a character.
- Compare adjacent words to extract ordering rules (edges in the graph).
- Use Kahn's algorithm for topological sorting to find a valid order.
- If there's a cycle or an invalid prefix situation (e.g., "abc" before "ab"), return that no valid order exists.

 Input Format (via user input):
1. First, an integer N representing the number of words.
2. Then N lines follow, each containing one word in lexicographical order of the alien language.

 Output:
- The correct order of characters in the alien language.
- Or a message stating that no valid character order exists.

 Sample Input:
Enter number of words: 5
Enter 5 words (sorted according to alien dictionary):
wrt
wrf
er
ett
rftt

 Sample Output:
Alien character order: wertf

Invalid Input Example:
Enter number of words: 2
Enter 2 words (sorted according to alien dictionary):
abc
ab

Output:
No valid character order exists.
Note:
- Handles edge cases like prefix conflicts.
- Detects cycles in character dependency graph.
- Uses standard Java collections (HashMap, HashSet, Queue).

*/

import java.util.*;

public class AlienDictionary {

    public static String alienOrder(String[] words) {
        Map<Character, Set<Character>> graph = new HashMap<>();
        Map<Character, Integer> inDegree = new HashMap<>();

        for (String word : words) {
            for (char c : word.toCharArray()) {
                graph.putIfAbsent(c, new HashSet<>());
                inDegree.putIfAbsent(c, 0);
            }
        }

        for (int i = 0; i < words.length - 1; i++) {
            String w1 = words[i];
            String w2 = words[i + 1];
            int minLen = Math.min(w1.length(), w2.length());
            boolean foundOrder = false;

            for (int j = 0; j < minLen; j++) {
                char c1 = w1.charAt(j);
                char c2 = w2.charAt(j);
                if (c1 != c2) {
                    if (!graph.get(c1).contains(c2)) {
                        graph.get(c1).add(c2);
                        inDegree.put(c2, inDegree.get(c2) + 1);
                    }
                    foundOrder = true;
                    break;
                }
            }

            if (!foundOrder && w1.length() > w2.length()) {
                return "";
            }
        }

        Queue<Character> queue = new LinkedList<>();
        for (char c : inDegree.keySet()) {
            if (inDegree.get(c) == 0) {
                queue.offer(c);
            }
        }

        StringBuilder order = new StringBuilder();
        while (!queue.isEmpty()) {
            char curr = queue.poll();
            order.append(curr);

            for (char neighbor : graph.get(curr)) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.offer(neighbor);
                }
            }
        }

        return order.length() == graph.size() ? order.toString() : "";
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of words: ");
        int n = Integer.parseInt(sc.nextLine());

        String[] words = new String[n];
        System.out.println("Enter " + n + " words (sorted according to alien dictionary):");
        for (int i = 0; i < n; i++) {
            words[i] = sc.nextLine().trim();
        }

        String order = alienOrder(words);
        if (order.isEmpty()) {
            System.out.println("No valid character order exists.");
        } else {
            System.out.println("Alien character order: " + order);
        }
    }
}
