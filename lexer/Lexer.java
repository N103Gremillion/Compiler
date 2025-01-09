package lexer;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {

    List<Pattern> patterns;
    List<Type> types;
    List<Token> tokens;
    String fileSource;
    int line;

    public Lexer (){
        this.line = 0;
        this.tokens = new ArrayList<>();
        this.patterns = new ArrayList<>();
        this.types = new ArrayList<>();
        addPatterns(this.patterns);
    }

    @Override
    public String toString() {

        String result = "";

        for (Token token : tokens) {
            String tokType = Token.tokenTypeToString(token.type);
            String tokVal = token.value;
            result += String.format("%s | %s\n", tokVal, tokType);
        }

        return result;
    }

    public void setFile(String fileString) {
        this.fileSource = fileString;
    }

    public void tokenize() {
        try (FileReader r = new FileReader(this.fileSource)) {
            int i;
            StringBuilder text = new StringBuilder();
            boolean inWord = false; 

            while ((i = r.read()) != -1) {
                char cur = (char) i;

                if (Character.isWhitespace(cur)) {
                    if (text.length() > 0) {
                        processText(text.toString());
                        text.setLength(0);
                    }
                } 
                else if (isSeparator(cur)) {
                    if (text.length() > 0) {
                        processText(text.toString());
                        text.setLength(0);
                    }
                    text.append(cur);
                    processText(text.toString());
                    text.setLength(0);
                }
                else {
                text.append(cur);
                }
            }

            if (text.length() > 0) {
                processText(text.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processText(String tokenText) {
        boolean matched = false;

        for (int j = 0; j < patterns.size(); j++) {
            Pattern pattern = patterns.get(j);
            Matcher matcher = pattern.matcher(tokenText);

            if (matcher.matches()) {
                Token token = new Token(types.get(j), tokenText);
                tokens.add(token);
                matched = true;
                break;
            }
        }

        if (!matched) {
            System.out.println("Invalid token: " + tokenText);
        }
    }

    private boolean isSeparator(char c) {
        if (c == ',' || c == ';' || c == '(' || c == ')' || c == '{' || c == '}' || c == '[' || c == ']'){
            return true;
        }
        return false;
    }

    public void addPatterns(List<Pattern> patterns) {
         // 1. Null Literal
        patterns.add(Pattern.compile("null"));  
        types.add(Type.NULL_LITERAL);

        // 2. Literals 
        patterns.add(Pattern.compile("\"[^\"]*\"")); 
        types.add(Type.STRING_LITERAL);

        patterns.add(Pattern.compile("\\d+\\.\\d+|\\d*\\.\\d+")); 
        types.add(Type.FLOAT_LITERAL);

        patterns.add(Pattern.compile("\\d+")); 
        types.add(Type.INTEGER_LITERAL);

        // 3. Parentheses, Brackets, and Curly Braces
        patterns.add(Pattern.compile("\\(")); 
        types.add(Type.OPEN_PAREN);

        patterns.add(Pattern.compile("\\)")); 
        types.add(Type.CLOSE_PAREN);

        patterns.add(Pattern.compile("\\[")); 
        types.add(Type.OPEN_BRACKET);

        patterns.add(Pattern.compile("\\]")); 
        types.add(Type.CLOSE_BRACKET);

        patterns.add(Pattern.compile("\\{")); 
        types.add(Type.OPEN_CURLY);

        patterns.add(Pattern.compile("\\}")); 
        types.add(Type.CLOSE_CURLY);

        // 4. Operators 
        patterns.add(Pattern.compile("\\+\\+")); 
        types.add(Type.PLUS_PLUS);

        patterns.add(Pattern.compile("--")); 
        types.add(Type.MINUS_MINUS);

        patterns.add(Pattern.compile("\\+\\=")); 
        types.add(Type.PLUS_EQUALS);

        patterns.add(Pattern.compile("-\\=")); 
        types.add(Type.MINUS_EQUALS);

        patterns.add(Pattern.compile("/\\=")); 
        types.add(Type.SLASH_EQUALS);

        patterns.add(Pattern.compile("\\*\\=")); 
        types.add(Type.STAR_EQUALS);

        patterns.add(Pattern.compile("\\+")); 
        types.add(Type.PLUS);

        patterns.add(Pattern.compile("-")); 
        types.add(Type.DASH);

        patterns.add(Pattern.compile("/")); 
        types.add(Type.SLASH);

        patterns.add(Pattern.compile("\\*")); 
        types.add(Type.STAR);

        patterns.add(Pattern.compile("%")); 
        types.add(Type.PERCENT);

        patterns.add(Pattern.compile("\\="));
        types.add(Type.ASSIGNMENT);

        patterns.add(Pattern.compile("==")); 
        types.add(Type.EQUALS);

        patterns.add(Pattern.compile("!=")); 
        types.add(Type.NOT_EQUALS);

        patterns.add(Pattern.compile("!")); 
        types.add(Type.NOT);

        patterns.add(Pattern.compile("<"));
        types.add(Type.LESS);

        patterns.add(Pattern.compile("<=")); 
        types.add(Type.LESS_EQUALS);

        patterns.add(Pattern.compile(">")); 
        types.add(Type.GREATER);

        patterns.add(Pattern.compile(">=")); 
        types.add(Type.GREATER_EQUALS);

        patterns.add(Pattern.compile("\\|\\|")); 
        types.add(Type.OR);

        patterns.add(Pattern.compile("&&")); 
        types.add(Type.AND);

        // 5. Delimiters
        patterns.add(Pattern.compile("\\;")); 
        types.add(Type.SEMICOLON);

        patterns.add(Pattern.compile("\\:")); 
        types.add(Type.COLON);

        patterns.add(Pattern.compile("\\?")); 
        types.add(Type.QUESTION);

        patterns.add(Pattern.compile("\\,")); 
        types.add(Type.COMMA);

        // 6. Java Keywords 
        patterns.add(Pattern.compile("if")); 
        types.add(Type.IF);

        patterns.add(Pattern.compile("else")); 
        types.add(Type.ELSE);

        patterns.add(Pattern.compile("switch")); 
        types.add(Type.SWITCH);

        patterns.add(Pattern.compile("case")); 
        types.add(Type.CASE);

        patterns.add(Pattern.compile("default")); 
        types.add(Type.DEFAULT);

        patterns.add(Pattern.compile("while")); 
        types.add(Type.WHILE);

        patterns.add(Pattern.compile("do")); 
        types.add(Type.DO);

        patterns.add(Pattern.compile("for")); 
        types.add(Type.FOR);

        patterns.add(Pattern.compile("break")); 
        types.add(Type.BREAK);

        patterns.add(Pattern.compile("continue")); 
        types.add(Type.CONTINUE);

        patterns.add(Pattern.compile("return")); 
        types.add(Type.RETURN);

        patterns.add(Pattern.compile("try")); 
        types.add(Type.TRY);

        patterns.add(Pattern.compile("catch")); 
        types.add(Type.CATCH);

        patterns.add(Pattern.compile("finally")); 
        types.add(Type.FINALLY);

        patterns.add(Pattern.compile("throw")); 
        types.add(Type.THROW);

        patterns.add(Pattern.compile("throws")); 
        types.add(Type.THROWS);

        patterns.add(Pattern.compile("class")); 
        types.add(Type.CLASS);

        patterns.add(Pattern.compile("interface")); 
        types.add(Type.INTERFACE);

        patterns.add(Pattern.compile("enum")); 
        types.add(Type.ENUM);

        patterns.add(Pattern.compile("extends")); 
        types.add(Type.EXTENDS);

        patterns.add(Pattern.compile("implements")); 
        types.add(Type.IMPLEMENTS);

        patterns.add(Pattern.compile("new")); 
        types.add(Type.NEW);

        patterns.add(Pattern.compile("abstract")); 
        types.add(Type.ABSTRACT);

        patterns.add(Pattern.compile("final")); 
        types.add(Type.FINAL);

        patterns.add(Pattern.compile("static")); 
        types.add(Type.STATIC);

        patterns.add(Pattern.compile("strictfp")); 
        types.add(Type.STRICTFP);

        patterns.add(Pattern.compile("transient")); 
        types.add(Type.TRANSIENT);

        patterns.add(Pattern.compile("volatile")); 
        types.add(Type.VOLATILE);

        patterns.add(Pattern.compile("private")); 
        types.add(Type.PRIVATE);

        patterns.add(Pattern.compile("protected")); 
        types.add(Type.PROTECTED);

        patterns.add(Pattern.compile("public"));
        types.add(Type.PUBLIC);

        patterns.add(Pattern.compile("import")); 
        types.add(Type.IMPORT);

        patterns.add(Pattern.compile("package")); 
        types.add(Type.PACKAGE);

        patterns.add(Pattern.compile("super")); 
        types.add(Type.SUPER);

        patterns.add(Pattern.compile("this")); 
        types.add(Type.THIS);

        // 7. Data Types
        patterns.add(Pattern.compile("boolean"));
        types.add(Type.BOOLEAN);

        patterns.add(Pattern.compile("byte")); 
        types.add(Type.BYTE);

        patterns.add(Pattern.compile("char")); 
        types.add(Type.CHAR);

        patterns.add(Pattern.compile("double")); 
        types.add(Type.DOUBLE);

        patterns.add(Pattern.compile("float")); 
        types.add(Type.FLOAT);

        patterns.add(Pattern.compile("int")); 
        types.add(Type.INT);

        patterns.add(Pattern.compile("long")); 
        types.add(Type.LONG);

        patterns.add(Pattern.compile("short")); 
        types.add(Type.SHORT);

        // 8. Lambda Operator
        patterns.add(Pattern.compile("->")); 
        types.add(Type.LAMBDA_OPERATOR);

        // 9. Identifiers 
        patterns.add(Pattern.compile("[a-zA-Z_][a-zA-Z0-9_]*")); 
        types.add(Type.IDENTIFIER);

        
         
    }
    
}