package org.scratch;

import org.scratch.execution.GameExecutor;
import org.scratch.execution.utils.ConfigLoader;
import org.scratch.pojo.Config;

import java.io.IOException;

public class GameApplication {
    public static void main(String[] args) throws IOException {
        if (args.length < 3)
            throw new IOException("Usage: java ScratchGame <config-path> <betting-amount>");

        String configPath = args[1];
        Integer betAmount = Integer.parseInt(args[3]);

        ConfigLoader configLoader = new ConfigLoader();
        Config config = configLoader.loadConfig(configPath);
        GameExecutor gameExecutor = new GameExecutor(config, betAmount);
        gameExecutor.execution();
    }
}