package org.scratch.execution.utils;

import org.scratch.pojo.Config;

import java.util.Map;
import java.util.Random;

public class GenerateMatrix {

    private final Config config;
    private final Random random = new Random();

    public GenerateMatrix(Config config) {
        this.config = config;
    }

    public String[][] generateMatrix() {
        Integer rows = config.rows();
        Integer columns = config.columns();
        String[][] matrix = new String[rows][columns];

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                matrix[row][column] = genRandomStandardSymbol(row, column);
            }
        }

        genRandomBonusSymbol(matrix);

        return matrix;
    }

    private String genRandomStandardSymbol(Integer row, Integer column) {
        Map<String, Integer> symbolProbabilities = config.
                probabilities().
                standard_symbols().
                get(row * config.columns() + column)
                .symbols();
        int totalWeight = symbolProbabilities
                .values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();
        int randomValue = random.nextInt(totalWeight);

        for (Map.Entry<String, Integer> entry : symbolProbabilities.entrySet()) {
            randomValue -= entry.getValue();
            if (randomValue < 0) {
                return entry.getKey();
            }
        }

        return "MISS";
    }

    private void genRandomBonusSymbol(String[][] matrix) {
        Map<String, Integer> bonusProbabilities = config.probabilities().bonus_symbols().symbols();
        int totalWeight = bonusProbabilities.values().stream().mapToInt(Integer::intValue).sum();
        int randomValue = random.nextInt(totalWeight);

        for (Map.Entry<String, Integer> entry : bonusProbabilities.entrySet()) {
            randomValue -= entry.getValue();
            if (randomValue < 0) {
                int row = random.nextInt(config.rows());
                int col = random.nextInt(config.columns());
                matrix[row][col] = entry.getKey();
                break;
            }
        }
    }

}