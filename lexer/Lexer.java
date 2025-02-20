package lexer;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Lexer{

    List<Pattern> patterns;
    List<Type> types;
    List<Token> tokens;
    String fileSource;
    List<Character> file;
    int pos;
    int line;

    public Lexer (){
        this.line = 0;
        this.pos = 0;
        this.tokens = new ArrayList<>();
        this.patterns = new ArrayList<>();
        this.types = new ArrayList<>();
        this.file = new ArrayList<>();
        addPatterns(this.patterns);
    }

    @Override
    public String toString(){
        String result = "";
        int num = 1;
        for (Token token : tokens) {
            String tokType = Token.tokenTypeToString(token.type);
            String tokVal = token.value;
            result += String.format("%d | %s | %s\n", num, tokVal, tokType);
            num++;
        }
        return result;
    }

    public String getFileString() {
        char cur;
        String result = "";
        int i = 0;
        while (i < file.size()){
            cur = (char) file.get(i);
            result += cur;
            i++;
        }
        return result;
    }

    public void inputFile(String fileString) {
        this.fileSource = fileString;
        try (FileReader r = new FileReader(this.fileSource)) {
            int i;
            while ((i = r.read()) != -1) {
                char cur = (char) i;
                file.add(cur);
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testValueGetter() {
        String cur = getNextValue();
        while (cur != null) {
            System.out.print(cur);
            cur = getNextValue();
        }
    }

    public void testTokenizer() {
        Token token = getNextToken();
        int num = 1;
        while (token != null) {
            System.out.println(num + " | " + token.value + " | " + Token.tokenTypeToString(token.type));
            token = getNextToken();
            num++;
        }
    }

    public Token getNextToken() {
        String value = getNextValue();
        Type type = getTokenType(value);
        if (value == null || type == null) {
            return null;
        }
        return new Token(type, value);
    }

    public String getNextValue() {
        if (pos >= file.size()) {
            return "";  
        }
    
        StringBuilder value = new StringBuilder();
        boolean found = false;
        boolean inWord = false;
    
        while (!found) {
            char cur = getNextChar();
    
            if (cur == '\n') {
                line++;  // Track line number
            }
    
            if (cur == '\u0000') {
                return null;  // End of input
            }
    
            // Handle string literals
            if (cur == '"') {
                return getStringLiteral();  // Get the string literal
            }
    
            // Handle single-line comments
            if (cur == '/' && peekNextChar() == '/') {
                skipSingleLineComment();
                continue;
            }
    
            // Handle multi-line comments
            if (cur == '/' && peekNextChar() == '*') {
                skipMultiLineComment();
                continue;
            }
    
            // Handle whitespace (end of word if inWord is true)
            if (Character.isWhitespace(cur)) {
                if (inWord) {
                    found = true;
                }
                continue;  // Skip whitespace
            }
    
            // Handle word characters (identifier or part of a number)
            if (Character.isLetterOrDigit(cur) || cur == '_') {
                value.append(cur);
                inWord = true;  // We're in a word now
            } 
            // Handle increment/decrement operators (e.g., ++ or --)
            else if (inWord && (cur == '+' || cur == '-')) {
                char next = peekNextChar();
                if (next == cur) {  // Check if it's ++ or --
                    getNextChar();  // Consume the second + or -
                    found = true;   // We found the operator
                    return value.toString();  // Return the identifier first
                }
            }
            // Handle separators (like spaces or punctuation)
            else if (isSeparator(cur)) {
                if (inWord) {
                    found = true; 
                    decrementPos();  
                } else {
                    value.append(cur);  
                    found = true;
                }
            }
            else {
                // If you're in a word, handle the current character
                if (inWord) {
                    value.append(cur);
                } else {
                    inWord = true;  
                    value.append(cur);
                }
            }
        }
    
        // Return the remaining value, which is an identifier or operator
        return value.toString();
    }
    
    
    
    

    public char getNextChar() {
        if (pos >= file.size()){
            return '\u0000';
        }
        char c = file.get(pos);
        incrementPos();
        return c;
    }

    private String getStringLiteral() {
        StringBuilder literal = new StringBuilder();
        literal.append('"');

        while (pos < file.size()) {
            char cur = getNextChar();
            
            if (cur == '\\' && peekNextChar() == '"') {
                literal.append(cur);  
                cur = getNextChar();  
            }
            
            literal.append(cur);
            
            if (cur == '"') {
                break;
            }

            if (cur == '\u0000') {
                System.err.println("Error: unterminated string");
                return literal.toString();
            }
        }

        return literal.toString();
    }

    private void skipSingleLineComment() {
        while (pos < file.size()) {
            char cur = getNextChar();
            if (cur == '\n') {
                line++;
                break; // Stop at newline
            }
        }
    }

    private void skipMultiLineComment() {
        getNextChar(); 
        while (pos < file.size()) {
            char cur = getNextChar();
            if (cur == '*' && peekNextChar() == '/') {
                getNextChar(); 
                break; 
            }
            if (cur == '\n') {
                line++;
            }
        }
    }
    
    private char peekNextChar() {
        if (pos < file.size()) {
            return file.get(pos);
        }
        return '\u0000';
    }

    private void incrementPos() {
        pos++;
    }
    
    private void decrementPos() {
        pos--;
    }

    private Type getTokenType(String value){
        if (value == null) {
            return null;
        }
        boolean matched = false;
        Type type = null;
        for (int j = 0; j < patterns.size(); j++){
            Pattern pattern = patterns.get(j);
            Matcher matcher = pattern.matcher(value);
            if (matcher.matches()){
                type = types.get(j);
                matched = true;
                break;
            }
        }
        if (!matched){
            return null;
        }
        return type;
    }

    private boolean isSeparator(char c){
        return c == ',' || c == ';' || c == '(' || c == ')' || c == '{' || c == '}' || c == '[' || c == ']';
    }

    public void addPatterns(List<Pattern> patterns){
         // 1. Null Literal
        patterns.add(Pattern.compile("null"));  
        types.add(Type.LITERAL);

        // 2. Literals 
        patterns.add(Pattern.compile("\"[^\"]*\"")); 
        types.add(Type.LITERAL);

        patterns.add(Pattern.compile("\\d+\\.\\d+|\\d*\\.\\d+")); 
        types.add(Type.LITERAL);

        patterns.add(Pattern.compile("\\d+")); 
        types.add(Type.LITERAL);

        // 4. Operators 
        patterns.add(Pattern.compile("\\+\\+")); 
        types.add(Type.OPERATOR);

        patterns.add(Pattern.compile("--")); 
        types.add(Type.OPERATOR);

        patterns.add(Pattern.compile("\\+\\=")); 
        types.add(Type.OPERATOR);

        patterns.add(Pattern.compile("-\\=")); 
        types.add(Type.OPERATOR);

        patterns.add(Pattern.compile("/\\=")); 
        types.add(Type.OPERATOR);

        patterns.add(Pattern.compile("\\*\\=")); 
        types.add(Type.OPERATOR);

        patterns.add(Pattern.compile("\\+")); 
        types.add(Type.OPERATOR);

        patterns.add(Pattern.compile("-")); 
        types.add(Type.OPERATOR);

        patterns.add(Pattern.compile("/")); 
        types.add(Type.OPERATOR);

        patterns.add(Pattern.compile("\\*")); 
        types.add(Type.OPERATOR);

        patterns.add(Pattern.compile("%")); 
        types.add(Type.OPERATOR);

        patterns.add(Pattern.compile("\\="));
        types.add(Type.OPERATOR);

        patterns.add(Pattern.compile("==")); 
        types.add(Type.OPERATOR);

        patterns.add(Pattern.compile("%=")); 
        types.add(Type.OPERATOR);

        patterns.add(Pattern.compile("!=")); 
        types.add(Type.OPERATOR);

        patterns.add(Pattern.compile("!")); 
        types.add(Type.OPERATOR);

        patterns.add(Pattern.compile("<"));
        types.add(Type.OPERATOR);

        patterns.add(Pattern.compile("<=")); 
        types.add(Type.OPERATOR);

        patterns.add(Pattern.compile(">")); 
        types.add(Type.OPERATOR);

        patterns.add(Pattern.compile(">=")); 
        types.add(Type.OPERATOR);

        patterns.add(Pattern.compile("\\|\\|")); 
        types.add(Type.OPERATOR);

        patterns.add(Pattern.compile("&&")); 
        types.add(Type.OPERATOR);

        patterns.add(Pattern.compile("\\?")); 
        types.add(Type.OPERATOR);

        // 5. Delimiters
        patterns.add(Pattern.compile("\\(")); 
        types.add(Type.DELIMITER);

        patterns.add(Pattern.compile("\\)")); 
        types.add(Type.DELIMITER);

        patterns.add(Pattern.compile("\\[")); 
        types.add(Type.DELIMITER);

        patterns.add(Pattern.compile("\\]")); 
        types.add(Type.DELIMITER);

        patterns.add(Pattern.compile("\\{")); 
        types.add(Type.DELIMITER);

        patterns.add(Pattern.compile("\\}")); 
        types.add(Type.DELIMITER);

        patterns.add(Pattern.compile("\\;"));
        types.add(Type.DELIMITER);  

        patterns.add(Pattern.compile("\\:"));
        types.add(Type.DELIMITER);  

        patterns.add(Pattern.compile("\\,"));
        types.add(Type.DELIMITER);  

        // 6. Java Keywords 
        patterns.add(Pattern.compile("void"));
        types.add(Type.KEYWORD);

        patterns.add(Pattern.compile("if"));
        types.add(Type.KEYWORD);

        patterns.add(Pattern.compile("else"));
        types.add(Type.KEYWORD);

        patterns.add(Pattern.compile("switch"));
        types.add(Type.KEYWORD);

        patterns.add(Pattern.compile("case"));
        types.add(Type.KEYWORD);

        patterns.add(Pattern.compile("default"));
        types.add(Type.KEYWORD);

        patterns.add(Pattern.compile("while"));
        types.add(Type.KEYWORD);

        patterns.add(Pattern.compile("do"));
        types.add(Type.KEYWORD);

        patterns.add(Pattern.compile("for"));
        types.add(Type.KEYWORD);

        patterns.add(Pattern.compile("break"));
        types.add(Type.KEYWORD);

        patterns.add(Pattern.compile("continue"));
        types.add(Type.KEYWORD);

        patterns.add(Pattern.compile("return"));
        types.add(Type.KEYWORD);

        patterns.add(Pattern.compile("try"));
        types.add(Type.KEYWORD);

        patterns.add(Pattern.compile("catch"));
        types.add(Type.KEYWORD);

        patterns.add(Pattern.compile("finally"));
        types.add(Type.KEYWORD);

        patterns.add(Pattern.compile("throw"));
        types.add(Type.KEYWORD);

        patterns.add(Pattern.compile("throws"));
        types.add(Type.KEYWORD);

        patterns.add(Pattern.compile("class"));
        types.add(Type.KEYWORD);

        patterns.add(Pattern.compile("interface"));
        types.add(Type.KEYWORD);

        patterns.add(Pattern.compile("enum"));
        types.add(Type.KEYWORD);

        patterns.add(Pattern.compile("extends"));
        types.add(Type.KEYWORD);

        patterns.add(Pattern.compile("implements"));
        types.add(Type.KEYWORD);

        patterns.add(Pattern.compile("new"));
        types.add(Type.KEYWORD);

        patterns.add(Pattern.compile("abstract"));
        types.add(Type.KEYWORD);

        patterns.add(Pattern.compile("final"));
        types.add(Type.KEYWORD);

        patterns.add(Pattern.compile("static"));
        types.add(Type.KEYWORD);

        patterns.add(Pattern.compile("strictfp"));
        types.add(Type.KEYWORD);

        patterns.add(Pattern.compile("transient"));
        types.add(Type.KEYWORD);

        patterns.add(Pattern.compile("volatile"));
        types.add(Type.KEYWORD);

        patterns.add(Pattern.compile("private"));
        types.add(Type.KEYWORD);

        patterns.add(Pattern.compile("protected"));
        types.add(Type.KEYWORD);

        patterns.add(Pattern.compile("public"));
        types.add(Type.KEYWORD);

        patterns.add(Pattern.compile("import"));
        types.add(Type.KEYWORD);

        patterns.add(Pattern.compile("package"));
        types.add(Type.KEYWORD);

        patterns.add(Pattern.compile("super"));
        types.add(Type.KEYWORD);

        patterns.add(Pattern.compile("this"));
        types.add(Type.KEYWORD);

        // 7. Data Types
        patterns.add(Pattern.compile("boolean"));
        types.add(Type.DATATYPE);

        patterns.add(Pattern.compile("byte"));
        types.add(Type.DATATYPE);

        patterns.add(Pattern.compile("char"));
        types.add(Type.DATATYPE);

        patterns.add(Pattern.compile("double"));
        types.add(Type.DATATYPE);

        patterns.add(Pattern.compile("float"));
        types.add(Type.DATATYPE);

        patterns.add(Pattern.compile("int"));
        types.add(Type.DATATYPE);

        patterns.add(Pattern.compile("long"));
        types.add(Type.DATATYPE);

        patterns.add(Pattern.compile("short"));
        types.add(Type.DATATYPE);

        // 8. Lambda Operator
        patterns.add(Pattern.compile("->")); 
        types.add(Type.OPERATOR);
        
        // SPECIAL cahrs
        patterns.add(Pattern.compile("\\\\n")); // newline escape character
        types.add(Type.SPECIALCHARACTER);

        patterns.add(Pattern.compile("\\\\t")); // tab escape character
        types.add(Type.SPECIALCHARACTER);

        // comments
        // Single-line comment
        patterns.add(Pattern.compile("//.*"));
        types.add(Type.COMMENT);

        // Multi-line comment
        patterns.add(Pattern.compile("/\\*.*\\*/"));
        types.add(Type.COMMENT);

        // Multi-line comment (with newlines in between)
        patterns.add(Pattern.compile("/\\*([\\s\\S]*?)\\*/"));
        types.add(Type.COMMENT);

        patterns.add(Pattern.compile("[a-zA-Z_][a-zA-Z0-9_]*(\\.[a-zA-Z_][a-zA-Z0-9_]*)*(\\.\\*)?"));
        types.add(Type.IDENTIFIER);
    }
    
}