package org.scratch.pojo;

public record WinCombination(
        double reward_multiplier,
        String when,
        int count,
        String group
) {}
