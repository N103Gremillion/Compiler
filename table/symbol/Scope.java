package table.symbol;

public enum Scope {
    METHOD_SCOPE,    
    INSTANCE_SCOPE,  
    CLASS_SCOPE,     
    BLOCK_SCOPE,     
    CONSTRUCTOR_SCOPE, 
    PACKAGE_SCOPE;   

    @Override
    public String toString() {
        switch (this) {
            case METHOD_SCOPE:
                return "Method Scope";
            case INSTANCE_SCOPE:
                return "Instance Scope";
            case CLASS_SCOPE:
                return "Class Scope";
            case BLOCK_SCOPE:
                return "Block Scope";
            case CONSTRUCTOR_SCOPE:
                return "Constructor Scope";
            case PACKAGE_SCOPE:
                return "Package Scope";
            default:
                throw new IllegalArgumentException("Unexpected value: " + this);
        }
    }
}
