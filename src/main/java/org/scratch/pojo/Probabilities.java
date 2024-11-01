package org.scratch.pojo;

import java.util.List;

public record Probabilities(
    List<StandardSymbol> standard_symbols,
    BonusSymbol bonus_symbols
) {}
