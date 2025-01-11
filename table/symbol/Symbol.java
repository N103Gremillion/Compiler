package table.symbol;

import java.util.List;
import java.util.Objects;

public class Symbol {

    private String name;
    private Type type;
    private Scope scope;
    private int size;  // In bytes
    private int dimensions;
    private Kind kind;
    private int lineNum;
    private String modifiers;
    private Object defaultValue;
    private Symbol parentSymbol;
    // used for method params
    private List<Type> parameterTypes;

    public Symbol(String name, Type type) {
    this.name = name;
    this.type = type;
    }

    public Symbol(String name, Type type, Scope scope, int size, int dimensions, Kind kind, int lineNum) {
        this.name = name;
        this.type = type;
        this.scope = scope;
        this.size = size;
        this.dimensions = dimensions;
        this.kind = kind;
        this.lineNum = lineNum;
    }


    // Getters and setters...

    @Override
    public String toString() {
        return String.format("Symbol[name=%s, type=%s, scope=%s, size=%d, dimensions=%d, kind=%s, line=%d]",
            name, type, scope, size, dimensions, kind, lineNum);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Symbol symbol = (Symbol) obj;
        return name.equals(symbol.name) && type == symbol.type && scope == symbol.scope;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, scope);
    }
}
