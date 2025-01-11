package table.symbol;

public enum Type {
    // Primitive Data Types
    VOID,      
    INT,        
    FLOAT,    
    DOUBLE,     
    CHAR,      
    BOOLEAN,    

    // Composite Types
    STRING,    
    ARRAY,     
    LIST,      
    MAP,       
    SET,       
    TUPLE,    

    // User-Defined Types
    CLASS,     
    INTERFACE, 
    ENUM,      
    STRUCT,     
    GENERIC,    

    // Special Types
    FUNCTION,   
    NULL,       
    UNKNOWN,  
    CONSTANT,   
    TYPE_ALIAS, 

    // Miscellaneous
    PACKAGE,    
    MODULE,     
    VARIABLE,   
    PARAMETER   
}
