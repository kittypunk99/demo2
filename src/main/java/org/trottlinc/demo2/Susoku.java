package org.trottlinc.demo2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Susoku {
    public static boolean isSafe(int[][] mat, int row, int col, int num) {
        for (int x = 0; x < 9; x++)
            if (mat[row][x] == num) return false;
        for (int x = 0; x < 9; x++)
            if (mat[x][col] == num) return false;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (mat[i + row - (row % 3)][j + col - (col % 3)] == num) return false;

        return true;
    }

    public static boolean solveSusokuRec(int[][] mat, int row, int col) {
        if (row == 8 && col == 9) return true;
        if (col == 9) {
            row++;
            col = 0;
        }
        if (mat[row][col] != 0) return solveSusokuRec(mat, row, col + 1);
        for (int num = 1; num <= 9; num++) {
            if (isSafe(mat, row, col, num)) {
                mat[row][col] = num;
                if (solveSusokuRec(mat, row, col + 1)) return true;
                mat[row][col] = 0;
            }
        }
        return false;
    }

    static void solveSusoku(int[][] mat) {
        solveSusokuRec(mat, 0, 0);
    }

    public static int[][] parsePuzzle(String puzzle) {
        int[][] mat = new int[9][9];
        int index = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char ch = puzzle.charAt(index++);
                if (ch == '.') {
                    mat[i][j] = 0;
                } else {
                    mat[i][j] = ch - '0';
                }
            }
        }
        return mat;
    }

    public static void main(String[] args) throws IOException {
        String[] susokus = {"simple", "easy", "intermediate", "expert"};

        BufferedWriter writer = Files.newBufferedWriter(Path.of("susoku/solution.susoku"), StandardOpenOption.APPEND);
        for (String s : susokus) {
            String fileName = "susoku/" + s + ".sudoku";
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(": ");
                    String puzzleName = parts[0];
                    String puzzleString = parts[1];
                    int[][] mat = parsePuzzle(puzzleString);
                    System.out.println("Solving " + puzzleName);
                    solveSusoku(mat);
                    writer.write(printSusoku(mat)+"\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        writer.close();


    }

    public static String printSusoku(int[][] mat) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0) {
                s.append("+---+---+---+\n");
            }
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0) {
                    s.append("|");
                }
                if (mat[i][j] == 0) {
                    s.append(" ");
                } else {
                    s.append(mat[i][j]);
                }
            }
            s.append("|\n");
        }
        s.append("+---+---+---+\n");
        return s.toString();
    }

}
