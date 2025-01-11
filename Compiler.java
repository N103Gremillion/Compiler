import lexer.Lexer;

class Compiler {

    public Lexer lex;

    public Compiler() {
        lex = new Lexer();
    }

    public void compile(String fileName) {
        // lex the file
        lex.inputFile(fileName);
        System.out.print(lex.getFileString());
    }
}