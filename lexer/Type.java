package lexer;

public enum Type {
    KEYWORD,
    DATATYPE, // I mean still a keyword but may be helpful
    IDENTIFIER,
    LITERAL,
    OPERATOR,
    DELIMITER,
    COMMENT,
    SPECIALCHARACTER // things like \n and \"
}