package lexer;

public class Token {

    public String  value;
    public  Type type;

    public Token(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    // static functions
    public static void debug(Token token) {
        System.out.println(String.format("%s ())\n", tokenTypeToString(token.type)));
    } 

    public boolean typeIsIn(Type[] tokenTypes) {
        for (Type tokenType : tokenTypes) {
            if (tokenType == this.type) {
                return true;
            }
        }
        return false;    
    }
    
    public static String tokenTypeToString(Type type) {
        return switch (type) {
            case IDENTIFIER -> "identifier";
            case KEYWORD -> "keyword";
            case LITERAL -> "literal";
            case OPERATOR -> "operator";
            case DELIMITER -> "delimiter";
            case COMMENT -> "comment";
            case SPECIALCHARACTER -> "special character";
            default -> "";
        };
    }

}