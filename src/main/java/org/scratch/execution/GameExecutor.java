package org.scratch.execution;

import org.scratch.execution.utils.CalculateReward;
import org.scratch.execution.utils.GenerateMatrix;
import org.scratch.pojo.Config;

public record GameExecutor(Config config, Integer betAmount) {

    public void execution() {
        GenerateMatrix genMatrix = new GenerateMatrix(config);
        String[][] matrix = genMatrix.generateMatrix();

        CalculateReward calculateReward = new CalculateReward(config, betAmount);
        calculateReward.calculateReward(matrix);
    }
}