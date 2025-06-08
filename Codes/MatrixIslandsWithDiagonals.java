// 🔸 Problem 5: Matrix Islands with Diagonals
/*
This program counts the number of islands in a 2D matrix where each cell is either 0 or 1.
An island is defined as a group of adjacent 1s connected horizontally, vertically, or diagonally.
The program uses Depth First Search (DFS) to explore all connected cells for each island found.

Key Points:
- The `directions` array holds all 8 possible moves (up, down, left, right, and the 4 diagonals).
- The `dfs` method marks all cells connected to a given cell as visited.
- The `countIslands` method iterates through the matrix, and for each unvisited '1', starts a DFS and increments the island count.
- User inputs the matrix dimensions and the matrix itself (with 0s and 1s).

-------------------------
Sample Input:
Enter number of rows: 4
Enter number of columns: 5
Enter the matrix row-wise (0 or 1), space-separated:
1 1 0 0 0
0 1 0 0 1
1 0 0 1 1
0 0 0 0 0

Expected Output:
Number of islands: 2

-------------------------
Sample Input:
Enter number of rows: 3
Enter number of columns: 3
Enter the matrix row-wise (0 or 1), space-separated:
1 0 1
0 1 0
1 0 1

Expected Output:
Number of islands: 5

Explanation:
Each '1' cell in this example forms an isolated island except the middle cell (1,1), 
which is connected diagonally to others, but diagonal connections count only when adjacent.

*/


import java.util.*;
public class MatrixIslandsWithDiagonals {    
    private static final int[][] directions = {
        {-1, -1}, {-1, 0}, {-1, 1},
        {0, -1},           {0, 1},
        {1, -1},  {1, 0},  {1, 1}
    };

    private static void dfs(int[][] matrix, boolean[][] visited, int row, int col) {
        visited[row][col] = true;
        int rows = matrix.length;
        int cols = matrix[0].length;

        for (int[] d : directions) {
            int newRow = row + d[0];
            int newCol = col + d[1];

            if (newRow >= 0 && newRow < rows &&
                newCol >= 0 && newCol < cols &&
                !visited[newRow][newCol] &&
                matrix[newRow][newCol] == 1) {

                dfs(matrix, visited, newRow, newCol);
            }
        }
    }

    public static int countIslands(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        boolean[][] visited = new boolean[rows][cols];
        int count = 0;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (matrix[r][c] == 1 && !visited[r][c]) {
                    count++;
                    dfs(matrix, visited, r, c);
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of rows: ");
        int rows = Integer.parseInt(sc.nextLine());

        System.out.print("Enter number of columns: ");
        int cols = Integer.parseInt(sc.nextLine());

        int[][] matrix = new int[rows][cols];

        System.out.println("Enter the matrix row-wise (0 or 1), space-separated:");
        for (int i = 0; i < rows; i++) {
            String[] parts = sc.nextLine().trim().split("\\s+");
            while (parts.length != cols) {
                System.out.println("Line must have " + cols + " elements. Try again:");
                parts = sc.nextLine().trim().split("\\s+");
            }
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = Integer.parseInt(parts[j]);
            }
        }

        int islands = countIslands(matrix);
        System.out.println("Number of islands: " + islands);

        sc.close();
    }
}
