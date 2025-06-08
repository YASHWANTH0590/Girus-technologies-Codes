/* 🔸 Problem 3: Knights and Portals

This program finds the shortest path from the top-left corner (0,0) to the bottom-right corner 
of a grid, where each cell can be either empty ('.') or blocked ('#'). You can move up, down, 
left, or right to adjacent empty cells. Additionally, you are allowed to teleport exactly once 
from any empty cell to any other empty cell in the grid.

The approach uses a Breadth-First Search (BFS) algorithm with an extended state to track whether 
the teleport has been used. The visited array is 3D: visited[row][col][usedTeleport], where 
usedTeleport is 0 if teleport is unused, and 1 if teleport has been used.

Input format:
- First, enter the number of rows (an integer).
- Then, enter the number of columns (an integer).
- Then enter the grid, one row per line, with characters separated by spaces ('.' for empty, '#' for blocked).
- Each row must have exactly the specified number of columns.

Example Input:
5
5
...#.
##.#.
.....
.###.
.....

Expected output for the above input:
Shortest path length (with one teleport): 7

If no path exists even with one teleport, the output will be:
No valid path found.


*/

import java.util.*;

public class KnightsAndPortals {

    static class Cell {
        int row, col, usedTeleport;
        Cell(int r, int c, int ut) {
            row = r; col = c; usedTeleport = ut;
        }
    }

    public static int shortestPathWithTeleport(char[][] grid, int rows, int cols) {
        int[][] directions = {{1,0}, {-1,0}, {0,1}, {0,-1}};
        boolean[][][] visited = new boolean[rows][cols][2]; // [row][col][usedTeleport?]
        Queue<Cell> queue = new LinkedList<>();
        queue.offer(new Cell(0, 0, 0));
        visited[0][0][0] = true;

        int steps = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                Cell curr = queue.poll();

                if (curr.row == rows - 1 && curr.col == cols - 1) {
                    return steps;
                }

                // Normal moves
                for (int[] d : directions) {
                    int nr = curr.row + d[0];
                    int nc = curr.col + d[1];
                    if (nr >= 0 && nr < rows && nc >= 0 && nc < cols
                            && grid[nr][nc] == '.' && !visited[nr][nc][curr.usedTeleport]) {
                        visited[nr][nc][curr.usedTeleport] = true;
                        queue.offer(new Cell(nr, nc, curr.usedTeleport));
                    }
                }

                // Teleport (if not used yet)
                if (curr.usedTeleport == 0) {
                    for (int r = 0; r < rows; r++) {
                        for (int c = 0; c < cols; c++) {
                            if (grid[r][c] == '.' && !visited[r][c][1]) {
                                visited[r][c][1] = true;
                                queue.offer(new Cell(r, c, 1));
                            }
                        }
                    }
                }
            }

            steps++;
        }
        return -1;  // No path found
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of rows: ");
        int rows = Integer.parseInt(sc.nextLine());
        System.out.print("Enter number of columns: ");
        int cols = Integer.parseInt(sc.nextLine());

        char[][] grid = new char[rows][cols];
        System.out.println("Enter the grid ('.' for empty, '#' for blocked), space-separated:");

        for (int i = 0; i < rows; i++) {
            String[] parts = sc.nextLine().trim().split("\\s+");
            while (parts.length != cols) {
                System.out.println("Line must have " + cols + " characters. Try again:");
                parts = sc.nextLine().trim().split("\\s+");
            }
            for (int j = 0; j < cols; j++) {
                grid[i][j] = parts[j].charAt(0);
            }
        }

        int result = shortestPathWithTeleport(grid, rows, cols);
        if (result == -1) {
            System.out.println("No valid path found.");
        } else {
            System.out.println("Shortest path length (with one teleport): " + result);
        }

        sc.close();
    }
}
