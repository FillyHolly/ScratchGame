package org.scratch.execution.utils;

public class CombinationChecker {

    public int checkHorizontalWithAdjacent(String[][] matrix, String symbol) {
        int rows = matrix.length;
        int columns = matrix[0].length;
        int countSymbols = 0;
        boolean[][] counted = new boolean[rows][columns];

        for (int row = 0; row < rows; row++) {
            boolean isWinningLine = true;

            for (int col = 0; col < columns; col++) {
                if (!matrix[row][col].equals(symbol)) {
                    isWinningLine = false;
                    break;
                }
            }

            if (isWinningLine) {
                for (int col = 0; col < columns; col++) {
                    if (!counted[row][col]) {
                        countSymbols++;
                        counted[row][col] = true;
                    }
                }

                if (row > 0) {
                    for (int col = 0; col < columns; col++) {
                        if (matrix[row - 1][col].equals(symbol) && !counted[row - 1][col]) {
                            countSymbols++;
                            counted[row - 1][col] = true;
                        }
                    }
                }

                if (row < rows - 1) {
                    for (int col = 0; col < columns; col++) {
                        if (matrix[row + 1][col].equals(symbol) && !counted[row + 1][col]) {
                            countSymbols++;
                            counted[row + 1][col] = true;
                        }
                    }
                }
            }
        }

        return countSymbols;
    }



    public int checkVerticalWithAdjacent(String[][] matrix, String symbol) {
        int rows = matrix.length;
        int columns = matrix[0].length;
        int countSymbols = 0;

        for (int col = 0; col < columns; col++) {
            boolean isWinningLine = true;
            for (int row = 0; row < rows; row++) {
                if (!matrix[row][col].equals(symbol)) {
                    isWinningLine = false;
                    break;
                }
            }

            if (isWinningLine) {
                if (col > 0) {
                    for (String[] strings : matrix) {
                        if (strings[col - 1].equals(symbol)) {
                            countSymbols++;
                        }
                    }
                }
                if (col < columns - 1) {
                    for (String[] strings : matrix) {
                        if (strings[col + 1].equals(symbol)) {
                            countSymbols++;
                        }
                    }
                }
                countSymbols += Math.min(rows, columns);
            }
        }
        return countSymbols;
    }

    public int checkDiagonalLeftToRightWithAdjacent(String[][] matrix, String symbol) {
        int rows = matrix.length;
        int columns = matrix[0].length;
        int countSymbols = 0;
        boolean isWinningLine = true;

        for (int i = 0; i < Math.min(rows, columns); i++) {
            if (!matrix[i][i].equals(symbol)) {
                isWinningLine = false;
                break;
            }
        }

        if (isWinningLine) {
            for (int i = 0; i < Math.min(rows, columns); i++) {
                if (i + 1 < columns && matrix[i][i + 1].equals(symbol)) {
                    countSymbols++;
                }

                if (i + 1 < rows && matrix[i + 1][i].equals(symbol)) {
                    countSymbols++;
                }
            }

            if (1 < columns && matrix[0][2].equals(symbol)) {
                countSymbols++;
            }

            if (rows - 2 >= 0 && matrix[2][0].equals(symbol)) {
                countSymbols++;
            }

            countSymbols += Math.min(rows, columns);
        }
        return countSymbols;
    }

    public int checkDiagonalRightToLeftWithAdjacent(String[][] matrix, String symbol) {
        int rows = matrix.length;
        int columns = matrix[0].length;
        int countSymbols = 0;
        boolean isWinningLine = true;

        for (int i = 0; i < Math.min(rows, columns); i++) {
            if (!matrix[i][columns - i - 1].equals(symbol)) {
                isWinningLine = false;
                break;
            }
        }

        if (isWinningLine) {
            for (int i = 0; i < Math.min(rows, columns); i++) {
                if (columns - i - 2 >= 0 && matrix[i][columns - i - 2].equals(symbol)) {
                    countSymbols++;
                }

                if (i + 1 < rows && matrix[i + 1][columns - i - 1].equals(symbol)) {
                    countSymbols++;
                }
            }

            if (columns - 3 >= 0 && matrix[0][0].equals(symbol)) {
                countSymbols++;
            }

            if (2 < rows && columns - 1 >= 0 && matrix[2][2].equals(symbol)) {
                countSymbols++;
            }

            countSymbols += Math.min(rows, columns);
        }
        return countSymbols;
    }

    public boolean checkHorizontal(String[][] matrix, String symbol) {
        for (String[] row : matrix) {
            boolean match = true;
            for (String cell : row) {
                if (!cell.equals(symbol)) {
                    match = false;
                    break;
                }
            }
            if (match) {
                return true;
            }
        }
        return false;
    }

    public boolean checkVertical(String[][] matrix, String symbol) {
        int columns = matrix[0].length;
        for (int col = 0; col < columns; col++) {
            boolean match = true;
            for (String[] row : matrix) {
                if (!row[col].equals(symbol)) {
                    match = false;
                    break;
                }
            }
            if (match) {
                return true;
            }
        }
        return false;
    }

    public boolean checkLeftToRightDiagonal(String[][] matrix, String symbol) {
        int rows = matrix.length;
        int columns = matrix[0].length;

        boolean leftToRightMatch = true;
        for (int i = 0; i < Math.min(rows, columns); i++) {
            if (!matrix[i][i].equals(symbol)) {
                leftToRightMatch = false;
                break;
            }
        }
        return leftToRightMatch;
    }

    public boolean checkRightToLeftDiagonal(String[][] matrix, String symbol) {
        int rows = matrix.length;
        int columns = matrix[0].length;

        boolean rightToLeftMatch = true;
        for (int i = 0; i < Math.min(rows, columns); i++) {
            if (!matrix[i][columns - i - 1].equals(symbol)) {
                rightToLeftMatch = false;
                break;
            }
        }
        return rightToLeftMatch;
    }

    public boolean checkTriangle(String[][] matrix, String symbol) {
        int rows = matrix.length;
        int columns = matrix[0].length;

        int[][] directions = {
                {1, 0}, {0, 1}, {-1, 0}, {0, -1},
                {1, 1}, {-1, 1}, {1, -1}, {-1, -1}
        };

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (matrix[row][col].equals(symbol)) {
                    for (int i = 0; i < directions.length; i++) {
                        for (int j = i + 1; j < directions.length; j++) {
                            int[] dir1 = directions[i];
                            int[] dir2 = directions[j];

                            int row1 = row + dir1[0], col1 = col + dir1[1];
                            int row2 = row + dir2[0], col2 = col + dir2[1];

                            if (isInBounds(row1, col1, rows, columns) &&
                                    isInBounds(row2, col2, rows, columns) &&
                                    matrix[row1][col1].equals(symbol) &&
                                    matrix[row2][col2].equals(symbol)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean isInBounds(int row, int col, int rows, int columns) {
        return row >= 0 && row < rows && col >= 0 && col < columns;
    }

}
