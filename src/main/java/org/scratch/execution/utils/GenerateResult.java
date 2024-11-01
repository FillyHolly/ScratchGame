package org.scratch.execution.utils;

import com.google.gson.*;
import java.util.*;

public class GenerateResult {

    public void generateResultJson(
            String[][] matrix,
            double totalReward,
            String appliedBonusSymbol,
            Map<String, List<String>> appliedWinningCombinations
    ) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("reward", totalReward);
        result.put("applied_winning_combinations", appliedWinningCombinations);
        result.put("applied_bonus_symbol", appliedBonusSymbol);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonWithoutMatrix = gson.toJson(result);
        StringBuilder matrixJson = new StringBuilder();
        matrixJson.append("\"matrix\": [\n");
        for (int i = 0; i < matrix.length; i++) {
            matrixJson.append("  [");
            for (int j = 0; j < matrix[i].length; j++) {
                matrixJson.append("\"").append(matrix[i][j]).append("\"");
                if (j < matrix[i].length - 1) {
                    matrixJson.append(", ");
                }
            }
            matrixJson.append("]");
            if (i < matrix.length - 1) {
                matrixJson.append(",\n");
            }
        }
        matrixJson.append("\n]");
        String finalJson = "{\n  " + matrixJson + "," + jsonWithoutMatrix.substring(1);

        System.out.println(finalJson);
    }

}
