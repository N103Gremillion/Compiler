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
        if (token.typeIsIn(new Type[] {Type.IDENTIFIER, Type.INTEGER_LITERAL, Type.FLOAT_LITERAL, Type.STRING_LITERAL})) {
            System.out.println(String.format("%s (%s)\n", tokenTypeToString(token.type), token.value));
        }
        else {
            System.out.println(String.format("%s ())\n", tokenTypeToString(token.type)));
        }
    } 

    public boolean typeIsIn(Type[] tokenTypes) {
        for (Type type : tokenTypes) {
            if (type == this.type) {
                return true;
            }
        }
        return false;    
    }
    
    public static String tokenTypeToString(Type type) {
        return switch (type) {
            case IDENTIFIER -> "identifier";
            case INTEGER_LITERAL -> "int_literal";
            case FLOAT_LITERAL -> "float_literal";
            case STRING_LITERAL -> "string_literal";
            case NULL_LITERAL -> "null";
            case OPEN_BRACKET -> "open_bracket";
            case CLOSE_BRACKET -> "close_bracket";
            case OPEN_CURLY -> "open_curly";
            case CLOSE_CURLY -> "close_curly";
            case OPEN_PAREN -> "open_paren";
            case CLOSE_PAREN -> "close_paren";
            case ASSIGNMENT -> "assignment";
            case EQUALS -> "equals";
            case NOT -> "not";
            case NOT_EQUALS -> "not_equals";
            case LESS -> "less";
            case LESS_EQUALS -> "less_equals";
            case GREATER -> "greater";
            case GREATER_EQUALS -> "greater_equals";
            case OR -> "or";
            case AND -> "and";
            case SEMICOLON -> "semicolon";
            case COLON -> "colon";
            case QUESTION -> "question";
            case COMMA -> "comma";
            case PLUS -> "plus";
            case DASH -> "dash";
            case SLASH -> "slash";
            case STAR -> "star";
            case PERCENT -> "percent";
            case PLUS_PLUS -> "plus_plus";
            case MINUS_MINUS -> "minus_minus";
            case PLUS_EQUALS -> "plus_equals";
            case MINUS_EQUALS -> "minus_equals";
            case SLASH_EQUALS -> "slash_equals";
            case STAR_EQUALS -> "star_equals";
            case IF -> "if";
            case ELSE -> "else";
            case SWITCH -> "switch";
            case CASE -> "case";
            case DEFAULT -> "default";
            case WHILE -> "while";
            case DO -> "do";
            case FOR -> "for";
            case BREAK -> "break";
            case CONTINUE -> "continue";
            case RETURN -> "return";
            case TRY -> "try";
            case CATCH -> "catch";
            case FINALLY -> "finally";
            case THROW -> "throw";
            case THROWS -> "throws";
            case CLASS -> "class";
            case INTERFACE -> "interface";
            case ENUM -> "enum";
            case EXTENDS -> "extends";
            case IMPLEMENTS -> "implements";
            case NEW -> "new";
            case ABSTRACT -> "abstract";
            case FINAL -> "final";
            case STATIC -> "static";
            case STRICTFP -> "strictfp";
            case TRANSIENT -> "transient";
            case VOLATILE -> "volatile";
            case PRIVATE -> "private";
            case PROTECTED -> "protected";
            case PUBLIC -> "public";
            case IMPORT -> "import";
            case PACKAGE -> "package";
            case SUPER -> "super";
            case THIS -> "this";
            case BOOLEAN -> "boolean";
            case BYTE -> "byte";
            case CHAR -> "char";
            case DOUBLE -> "double";
            case FLOAT -> "float";
            case INT -> "int";
            case LONG -> "long";
            case SHORT -> "short";
            case LAMBDA_OPERATOR -> "lambda";
            default -> "";
        };
    }

}


