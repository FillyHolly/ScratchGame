package org.scratch.execution.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.scratch.pojo.Config;

import java.io.File;
import java.io.IOException;

public class ConfigLoader {

    ObjectMapper objectMapper = new ObjectMapper();

    public Config loadConfig(String configPath) throws IOException {
        return objectMapper.readValue(
                new File(configPath),
                Config.class
        );
    }

}
