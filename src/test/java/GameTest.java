import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.scratch.execution.GameExecutor;
import org.scratch.execution.utils.CalculateReward;
import org.scratch.execution.utils.ConfigLoader;
import org.scratch.execution.utils.GenerateMatrix;
import org.scratch.pojo.Config;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameTest {

    private GenerateMatrix genMatrix;
    private CalculateReward calculateReward;

    @BeforeEach
    public void init() throws IOException {
        ConfigLoader configLoader = new ConfigLoader();
        Config config = configLoader.loadConfig("path-to-json");
        genMatrix = new GenerateMatrix(config);
        GameExecutor gameExecutor = new GameExecutor(config, 100);
        calculateReward = new CalculateReward(gameExecutor.config(), gameExecutor.betAmount());
    }

    @Test
    void testGenerateMatrix() throws RuntimeException {
        String[][] matrix = genMatrix.generateMatrix();

        assertEquals(3, matrix.length);
        assertEquals(3, matrix[0].length);
    }

    @Test
    void testCalculateRewardLose() throws RuntimeException {
        String[][] matrix = {
                {"A", "D", "E"},
                {"E", "C", "B"},
                {"A", "E", "A"}
        };
        double reward = calculateReward.calculateReward(matrix);

        assertEquals(0, reward);
    }

    @Test
    void testCalculateRewardHorizontal() throws RuntimeException {
        String[][] matrix = {
                {"A", "F", "A"},
                {"C", "C", "B"},
                {"E", "E", "E"}
        };
        double reward = calculateReward.calculateReward(matrix);

        assertTrue(reward > 0);
        assertEquals(240, reward);
    }

    @Test
    void testCalculateRewardVertical() throws RuntimeException {
        String[][] matrix = {
                {"A", "A", "E"},
                {"D", "A", "A"},
                {"A", "A", "D"}
        };
        double reward = calculateReward.calculateReward(matrix);

        assertTrue(reward > 0);
        assertEquals(6000, reward);
    }

    @Test
    void testCalculateRewardDiagonal1() throws RuntimeException {
        String[][] matrix = {
                {"A", "D", "C"},
                {"E", "A", "B"},
                {"A", "E", "A"}
        };
        double reward = calculateReward.calculateReward(matrix);

        assertTrue(reward > 0);
        assertEquals(7500, reward);
    }

    @Test
    void testCalculateRewardDiagonal2() throws RuntimeException {
        String[][] matrix = {
                {"B", "D", "A"},
                {"E", "A", "B"},
                {"A", "E", "A"}
        };
        double reward = calculateReward.calculateReward(matrix);

        assertTrue(reward > 0);
        assertEquals(7500, reward);
    }

    @Test
    void testCalculateRewardTriangle1() throws RuntimeException {
        String[][] matrix = {
                {"B", "A", "D"},
                {"D", "A", "C"},
                {"A", "E", "F"}
        };
        double reward = calculateReward.calculateReward(matrix);

        assertTrue(reward > 0);
        assertEquals(2500, reward);
    }

    @Test
    void testCalculateRewardTriangle2() throws RuntimeException {
        String[][] matrix = {
                {"F", "A", "E"},
                {"E", "F", "A"},
                {"F", "B", "B"}
        };
        double reward = calculateReward.calculateReward(matrix);

        assertTrue(reward > 0);
        assertEquals(500, reward);
    }

    @Test
    void testCalculateRewardTriangle3() throws RuntimeException {
        String[][] matrix = {
                {"B", "A", "E"},
                {"F", "F", "A"},
                {"F", "B", "B"}
        };
        double reward = calculateReward.calculateReward(matrix);

        assertTrue(reward > 0);
        assertEquals(500, reward);
    }

    @Test
    void testWinCombination() throws RuntimeException {
        String[][] matrix = {
                {"A", "A", "B"},
                {"A", "+1000", "B"},
                {"A", "A", "B"}
        };
        double reward = calculateReward.calculateReward(matrix);

        assertEquals(6200, reward);
    }

    @Test
    void testWinCombination1() throws RuntimeException {
        String[][] matrix = {
                {"A", "A", "A"},
                {"A", "A", "A"},
                {"A", "A", "A"}
        };
        double reward = calculateReward.calculateReward(matrix);

        assertEquals(40000, reward);
    }

    @Test
    void testWinCombination2() throws RuntimeException {
        String[][] matrix = {
                {"A", "A", "A"},
                {"A", "A", "A"},
                {"A", "A", "B"}
        };
        double reward = calculateReward.calculateReward(matrix);

        assertEquals(20000, reward);
    }

    @Test
    void testWinCombination3() throws RuntimeException {
        String[][] matrix = {
                {"B", "A", "B"},
                {"A", "A", "A"},
                {"B", "A", "B"}
        };
        double reward = calculateReward.calculateReward(matrix);

        assertEquals(4000, reward);
    }

}
