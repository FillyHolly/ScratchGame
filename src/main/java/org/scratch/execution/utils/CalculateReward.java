package org.scratch.execution.utils;

import org.scratch.pojo.Config;
import org.scratch.pojo.Symbol;
import org.scratch.pojo.WinCombination;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;

public class CalculateReward {

    private final Config config;
    private final Integer betAmount;
    private boolean allSymbolsFlag = false;
    private String appliedBonusSymbol = "";
    private final Map<String, List<String>> appliedWinningCombinations = new HashMap<>();
    private final CombinationChecker combinationChecker = new CombinationChecker();
    private final GenerateResult generateResult = new GenerateResult();

    public CalculateReward(Config config, Integer betAmount) {
        this.config = config;
        this.betAmount = betAmount;
    }

    public double calculateReward(String[][] matrix) {
        double totalReward = 0;
        allSymbolsFlag = false;
        appliedWinningCombinations.clear();
        appliedBonusSymbol = "";

        Set<String> symbolsInMatrix = new HashSet<>();
        for (String[] row : matrix) {
            symbolsInMatrix.addAll(Arrays.asList(row));
        }

        symbolsInMatrix.removeIf(symbol ->
            !combinationChecker.checkHorizontal(matrix, symbol) &&
            !combinationChecker.checkVertical(matrix, symbol) &&
            !combinationChecker.checkLeftToRightDiagonal(matrix, symbol) &&
            !combinationChecker.checkRightToLeftDiagonal(matrix, symbol) &&
            !combinationChecker.checkTriangle(matrix, symbol)
        );

        for (String symbol : symbolsInMatrix) {
            Symbol symbolConfig = config.symbols().get(symbol);

            if (symbolConfig != null && symbolConfig.type().equals("standard")) {
                double symbolReward = calculateSymbolReward(matrix, symbol, symbolConfig);
                totalReward += symbolReward;
            }
        }

        totalReward = applyBonusSymbols(matrix, totalReward);
        generateResult.generateResultJson(matrix, totalReward, appliedBonusSymbol, appliedWinningCombinations);

        return totalReward;
    }

    private double calculateSymbolReward(String[][] matrix, String symbol, Symbol symbolConfig) {
        double symbolReward = betAmount * symbolConfig.reward_multiplier();

        if (combinationChecker.checkHorizontal(matrix, symbol)) {
            symbolReward *= 2;
            appliedWinningCombinations.computeIfAbsent(symbol, k -> new ArrayList<>()).add("same_symbol_horizontally");
            int allCountSymbols = combinationChecker.checkHorizontalWithAdjacent(matrix, symbol);

            return calculateRepeatReward(symbol, symbolReward, allCountSymbols);
        }
        if (combinationChecker.checkVertical(matrix, symbol)) {
            symbolReward *= 2;
            appliedWinningCombinations.computeIfAbsent(symbol, k -> new ArrayList<>()).add("same_symbol_vertically");
            int allCountSymbols = combinationChecker.checkVerticalWithAdjacent(matrix, symbol);

            return calculateRepeatReward(symbol, symbolReward, allCountSymbols);
        }
        if (combinationChecker.checkLeftToRightDiagonal(matrix, symbol)) {
            symbolReward *= 5;
            appliedWinningCombinations.computeIfAbsent(symbol, k -> new ArrayList<>()).add("same_symbol_lr_diagonally");
            int allCountSymbols = combinationChecker.checkDiagonalLeftToRightWithAdjacent(matrix, symbol);

            return calculateRepeatReward(symbol, symbolReward, allCountSymbols);
        }
        if (combinationChecker.checkRightToLeftDiagonal(matrix, symbol)) {
            symbolReward *= 5;
            appliedWinningCombinations.computeIfAbsent(symbol, k -> new ArrayList<>()).add("same_symbol_rl_diagonally");
            int allCountSymbols = combinationChecker.checkDiagonalRightToLeftWithAdjacent(matrix, symbol);

            return calculateRepeatReward(symbol, symbolReward, allCountSymbols);
        }
        if (combinationChecker.checkTriangle(matrix, symbol)) {
            symbolReward *= 5;
            appliedWinningCombinations.computeIfAbsent(symbol, k -> new ArrayList<>()).add("same_symbol_triangle");
        }

        return symbolReward;
    }

    private double calculateRepeatReward(String symbol, double symbolReward, int allCountSymbols) {
        if (allCountSymbols > 3 || allSymbolsFlag) {
            allSymbolsFlag = true;
            symbolReward *= 2;

            for (Map.Entry<String, WinCombination> combination : config.win_combinations().entrySet()) {
                if (combination.getValue().when().equals("same_symbols") && allCountSymbols == combination.getValue().count()) {
                    symbolReward *= combination.getValue().reward_multiplier();
                    appliedWinningCombinations.computeIfAbsent(symbol, k -> new ArrayList<>()).add(combination.getKey());
                }
            }
        }

        return symbolReward;
    }

    private double applyBonusSymbols(String[][] matrix, double currentReward) {
        for (String[] row : matrix) {
            for (String cell : row) {
                Symbol bonusSymbol = config.symbols().get(cell);
                if (bonusSymbol != null && "bonus".equals(bonusSymbol.type())) {
                    switch (bonusSymbol.impact()) {
                        case "multiply_reward":
                            currentReward *= bonusSymbol.reward_multiplier();
                            break;
                        case "extra_bonus":
                            currentReward += bonusSymbol.extra();
                            break;
                        case "miss":
                            break;
                    }
                    appliedBonusSymbol = cell;
                    return currentReward;
                }
            }
        }
        return currentReward;
    }
}