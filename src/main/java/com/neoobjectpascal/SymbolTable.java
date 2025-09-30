package com.neoobjectpascal;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    private final Map<String, Symbol> symbols = new HashMap<>();
    private final SymbolTable parent;

    public SymbolTable(SymbolTable parent) {
        this.parent = parent;
    }

    public SymbolTable() {
        this.parent = null;
    }

    public void put(String name, Symbol symbol) {
        symbols.put(name, symbol);
    }

    public Symbol get(String name) {
        Symbol symbol = symbols.get(name);
        if (symbol == null && parent != null) {
            return parent.get(name);
        }
        return symbol;
    }
}

