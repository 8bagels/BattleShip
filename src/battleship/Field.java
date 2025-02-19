package battleship;

import java.util.Scanner;
import java.util.ArrayList;

public class Field {
    static Scanner scanner = new Scanner(System.in);
    protected char[][] newGrid;
    ArrayList<ArrayList<ArrayList<Integer>>> shipsCoordinates = new ArrayList<ArrayList<ArrayList<Integer>>>();

    static Ships[] ships = Ships.values();

    Field() {
        newGrid = createGrid(10);
        start();
    }

    public char[][] createGrid(int size) {
        char[][] Grid = new char[size][size];

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                Grid[r][c] = '~';
            }
        }
        display(Grid);
        return Grid;
    }

    public void display(char[][] grid) {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        char[] rows = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J' };
        int i = 0;

        for (char[] c1 : grid) {
            System.out.print(rows[i] + " ");

            for (char c2 : c1) {
                System.out.print(c2 + " ");
            }
            System.out.println();
            i++;
        }
    }

    public void start() {
        for (int counter = 0; counter < ships.length; counter++) {
            int[] shipCoord = getInput(ships[counter].getName(), ships[counter].getSize());
            placeShip(shipCoord);
        }
    }

    public int[] getInput(String shipN, int shipL) {
        int[] coordinates;

        System.out.println("Enter the coordinates of the " + shipN + " (" + shipL + " cells): ");
        do {
            String input1 = scanner.next().toUpperCase();
            String input2 = scanner.next().toUpperCase();

            int row1 = input1.charAt(0) - 65;
            int col1 = Integer.parseInt(input1.substring(1)) - 1;
            int row2 = input2.charAt(0) - 65;
            int col2 = Integer.parseInt(input2.substring(1)) - 1;

            coordinates = new int[] { row1, col1, row2, col2 };
        } while (!validCoordinates(coordinates, shipN, shipL));
        return coordinates;
    }

    public boolean validCoordinates(int[] coord, String shipName, int shipSize) {
        int col = Math.abs(coord[1] - coord[3]);
        int row = Math.abs(coord[0] - coord[2]);

        if (!straightCheck(row, col)) {
            System.out.println("Error! Wrong ship location! Try again: ");
            return false;
        }
        if (!lengthCheck(shipName, shipSize, row, col)) {
            System.out.println("Error! Wrong length of the " + shipName + "! Try again: ");
            return false;
        }
        if (!tooClose(coord[0], coord[1], coord[2], coord[3])) {
            System.out.println("Error! You placed it too close to another one. Try again:");
            return false;
        }
        if (!isOccupied(coord[0], coord[1], coord[2], coord[3])) {
            System.out.println("Error! Not all the cells are free! Try again:");
            return false;
        }
        return true;
    }

    public boolean straightCheck(int r, int c) {
        if (c == 0 || r == 0) {
            return true;
        } else
            return false;
    }

    public boolean lengthCheck(String name, int size, int r, int c) {
        boolean hor = (r == 0) && (c == size - 1);
        boolean ver = (c == 0) && (r == size - 1);

        if (hor || ver) {
            return true;
        } else
            return false;
    }

    public boolean tooClose(int r1, int c1, int r2, int c2) {
        int rowUpper = Math.min(r1, r2) - 1;
        int rowLower = Math.max(r1, r2) + 1;
        int colUpper = Math.min(c1, c2) - 1;
        int colLower = Math.max(c1, c2) + 1;

        if (rowUpper < 0)
            rowUpper = 0;
        if (rowLower > 9)
            rowLower = 9;
        if (colUpper < 0)
            colUpper = 0;
        if (colLower > 9)
            colLower = 9;

        for (int k = rowUpper; k < rowLower + 1; k++) {
            for (int m = colUpper; m < colLower + 1; m++) {
                if (newGrid[k][m] == 'O') {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isOccupied(int r1, int c1, int r2, int c2) {
        for (int i = Math.min(r1, r2); i < Math.max(r1, r2) + 1; i++) {
            for (int j = Math.min(c1, c2); j < Math.max(c1, c2); j++) {
                if (newGrid[i][j] == 'O') {
                    return false;
                }
            }
        }
        return true;
    }

    public void placeShip(int[] shipCoord) {

        int row1 = shipCoord[0];
        int col1 = shipCoord[1];
        int row2 = shipCoord[2];
        int col2 = shipCoord[3];
        ArrayList<ArrayList<Integer>> ship = new ArrayList<ArrayList<Integer>>();

        for (int i = Math.min(row1, row2); i < Math.max(row1, row2) + 1; i++) {
            for (int j = Math.min(col1, col2); j < Math.max(col1, col2) + 1; j++) {
                newGrid[i][j] = 'O';
                ArrayList<Integer> coor = new ArrayList<Integer>();
                coor.add(i);
                coor.add(j);
                ship.add(coor);
            }
        }
        shipsCoordinates.add(ship);
        display(newGrid);
    }

    public char getElemet(int row, int col) {
        return newGrid[row][col];
    }

    public void updateElement(int row, int col, char character) {
        newGrid[row][col] = character;
    }

    public void setArray(ArrayList<ArrayList<ArrayList<Integer>>> oppArray) {
        shipsCoordinates = new ArrayList<ArrayList<ArrayList<Integer>>>();
        ArrayList<ArrayList<Integer>> newShip;

        for (int i = 0; i < oppArray.size(); i++) {
            newShip = new ArrayList<ArrayList<Integer>>();
            for (int j = 0; j < oppArray.get(i).size(); j++) {
                newShip.add(oppArray.get(i).get(j));
            }
            shipsCoordinates.add(newShip);
        }
    }
}
