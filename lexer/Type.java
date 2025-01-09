package lexer;

public enum Type {

    NULL_LITERAL,
    INTEGER_LITERAL,
    FLOAT_LITERAL,
    STRING_LITERAL,

    OPEN_BRACKET,
    CLOSE_BRACKET,
    OPEN_CURLY,
    CLOSE_CURLY,
    OPEN_PAREN,
    CLOSE_PAREN,

    ASSIGNMENT,
    EQUALS,
    NOT,
    NOT_EQUALS,

    LESS,
    LESS_EQUALS,
    GREATER,
    GREATER_EQUALS,

    OR,
    AND,

    SEMICOLON,
    COLON,
    QUESTION,
    COMMA,

    PLUS,
    DASH,
    SLASH,
    STAR,
    PERCENT,

    PLUS_PLUS,
    MINUS_MINUS,
    PLUS_EQUALS,
    MINUS_EQUALS,
    SLASH_EQUALS,
    STAR_EQUALS,

    // java keywords

    IF,
    ELSE,
    SWITCH,
    CASE,
    DEFAULT,
    WHILE,
    DO,
    FOR,
    BREAK,
    CONTINUE,
    RETURN,

    TRY,
    CATCH,
    FINALLY,
    THROW,
    THROWS,

    CLASS,
    INTERFACE,
    ENUM,
    EXTENDS,
    IMPLEMENTS,
    NEW,

    ABSTRACT,
    FINAL,
    STATIC,
    STRICTFP,
    TRANSIENT,
    VOLATILE,

    PRIVATE,
    PROTECTED,
    PUBLIC,

    IMPORT,
    PACKAGE,

    SUPER,
    THIS,

    BOOLEAN,
    BYTE,
    CHAR,
    DOUBLE,
    FLOAT,
    INT,
    LONG,
    SHORT,

    LAMBDA_OPERATOR,

    IDENTIFIER,
}