import lexer.Lexer;

class Compiler {
    public Lexer lex;

    public Compiler() {
        Lexer lex = new Lexer();
    }

    public void compile(String fileName) {
        // lex the file
        lex.setFile(fileName);
        lex.tokenize();
        System.out.println(lex.toString());
    }
}