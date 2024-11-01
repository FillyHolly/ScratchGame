package org.scratch.pojo;

import java.util.Map;

public record Config(
    Integer columns,
    Integer rows,
    Map<String, Symbol> symbols,
    Probabilities probabilities,
    Map<String, WinCombination> win_combinations
) {}
