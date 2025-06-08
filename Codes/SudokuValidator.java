//🔸 Problem 1: Sudoku Validator With Custom Zones

/*
This program validates a Sudoku board with both standard Sudoku rules and additional custom zones defined by the user. 
It reads a 9x9 Sudoku board from the user input, where digits 1-9 represent filled cells and '.' represents empty cells. 
Then, it reads one or more custom zones—each consisting of exactly 9 cell coordinates (row and column pairs). 
The validation checks that each row, each column, and each 3x3 sub-box contains no duplicate digits (ignoring empty cells). 
Additionally, it verifies that each custom zone also contains no duplicate digits. 
If all these conditions hold true, the program outputs "Sudoku is VALID"; otherwise, it outputs "Sudoku is INVALID". 
This allows flexibility for validating Sudoku variants with custom grouping of cells beyond the standard rules.

-------------------------
Sample Input (Valid Sudoku):

Board (9 lines, '.' for empty):
53..7....
6..195...
.98....6.
8...6...3
4..8.3..1
7...2...6
.6....28.
...419..5
....8..79

Custom zones (one line per zone, cells as row,col pairs):
0,0 0,1 0,2 1,0 1,1 1,2 2,0 2,1 2,2

Expected Output:
Sudoku is VALID

-------------------------
Sample Input (Invalid Sudoku - duplicate in custom zone):

Board (same as above)

Custom zones:
0,0 0,1 0,2 1,0 1,1 1,2 2,0 2,1 0,0   // Note the last cell repeated (0,0)

Expected Output:
Sudoku is INVALID

-------------------------
Sample Input (Invalid Sudoku - duplicate in row):

Board (first line modified to have duplicate '5'):
55..7....
6..195...
.98....6.
8...6...3
4..8.3..1
7...2...6
.6....28.
...419..5
....8..79

Custom zones:
0,0 0,1 0,2 1,0 1,1 1,2 2,0 2,1 2,2

Expected Output:
Sudoku is INVALID
*/



import java.util.*;

public class SudokuValidator {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Enter Sudoku board (9 lines, 9 chars each, . for empty):");
        char[][] board = new char[9][9];
        for (int i = 0; i < 9; i++) {
            board[i] = sc.nextLine().trim().toCharArray();
        }
        
        System.out.println("Enter custom zones (format: '0,0 0,1 0,2 ...' for each zone, one zone per line):");
        sc.nextLine(); 
        List<int[][]> zones = new ArrayList<>();
        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.isEmpty()) break;
            
            String[] cells = line.split(" ");
            int[][] zone = new int[9][2];
            for (int i = 0; i < 9; i++) {
                String[] coords = cells[i].split(",");
                zone[i][0] = Integer.parseInt(coords[0]);
                zone[i][1] = Integer.parseInt(coords[1]);
            }
            zones.add(zone);
        }
        
        boolean valid = isValidSudoku(board, zones.toArray(new int[0][][]));
        System.out.println("Sudoku is " + (valid ? "VALID" : "INVALID"));
    }

    public static boolean isValidSudoku(char[][] board, int[][][] customZones) {
        for (int i = 0; i < 9; i++) {
            if (!isValidUnit(board, i, 0, i, 8) || !isValidUnit(board, 0, i, 8, i)) {
                return false;
            }
        }
        
        for (int i = 0; i < 9; i += 3) {
            for (int j = 0; j < 9; j += 3) {
                if (!isValidUnit(board, i, j, i+2, j+2)) {
                    return false;
                }
            }
        }
        
        for (int[][] zone : customZones) {
            Set<Character> seen = new HashSet<>();
            for (int[] cell : zone) {
                char c = board[cell[0]][cell[1]];
                if (c != '.' && !seen.add(c)) {
                    return false;
                }
            }
        }
        
        return true;
    }

    private static boolean isValidUnit(char[][] board, int r1, int c1, int r2, int c2) {
        Set<Character> seen = new HashSet<>();
        for (int r = r1; r <= r2; r++) {
            for (int c = c1; c <= c2; c++) {
                char val = board[r][c];
                if (val != '.' && !seen.add(val)) {
                    return false;
                }
            }
        }
        return true;
    }
}















