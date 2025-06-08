/*
 * Problem 4: Bitwise Matching Pattern
 * 
 * Given an integer n, this program finds the next larger integer that has 
 * the same number of '1' bits in its binary representation as n.
 * 
 * Approach:
 * The algorithm works by analyzing the binary form of n:
 * - Counts trailing zeros (c0) and the consecutive ones after them (c1).
 * - Identifies the rightmost non-trailing zero bit.
 * - Flips this zero to one, clears bits to the right, then inserts (c1 - 1) ones at the rightmost positions.
 * This results in the smallest number greater than n with the same number of 1 bits.
 * If no such number exists (e.g., n is the largest with that number of 1s), it returns -1.
 * 
 * Time Complexity: O(1) — The number of bit operations is fixed (32-bit integer).
 * 
 * Sample Input:
 * Enter an integer n: 6
 * 
 * Explanation: 6 in binary is 110 (two 1s). The next larger integer with two 1s is 9 (binary 1001).
 * 
 * Sample Output:
 * Next larger integer with the same number of 1 bits: 9
 * 
 * Sample Input:
 * Enter an integer n: 15
 * 
 * Explanation: 15 in binary is 1111 (four 1s). The next larger integer with four 1s is 23 (binary 10111).
 * 
 * Sample Output:
 * Next larger integer with the same number of 1 bits: 23
 * 
 * Sample Input:
 * Enter an integer n: 2147483647
 * 
 * Explanation: 2147483647 is the largest 32-bit signed integer with all bits set to 1.
 * There is no larger integer with the same number of 1 bits.
 * 
 * Sample Output:
 * No larger integer with the same number of 1 bits exists.
 */

import java.util.Scanner;

public class BitwiseMatchingPattern {

    public static int nextLargerWithSameOnes(int n) {
        int c = n;
        int c0 = 0;
        int c1 = 0;

        while (((c & 1) == 0) && (c != 0)) {
            c0++;
            c >>= 1;
        }

        while ((c & 1) == 1) {
            c1++;
            c >>= 1;
        }

        if (c0 + c1 == 31 || c0 + c1 == 0) {
            return -1;
        }

        int p = c0 + c1;

        n |= (1 << p);

        n &= ~((1 << p) - 1);

        n |= (1 << (c1 - 1)) - 1;

        return n;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter an integer n: ");
        int n = sc.nextInt();

        int result = nextLargerWithSameOnes(n);
        if (result == -1) {
            System.out.println("No larger integer with the same number of 1 bits exists.");
        } else {
            System.out.println("Next larger integer with the same number of 1 bits: " + result);
        }

        sc.close();
    }
}
