import lexer.Lexer;

class JavaCompiler {

    public Lexer lex;

    public JavaCompiler() {
        lex = new Lexer();
    }

    public void compile(String fileName) {
        // lex the file
        lex.inputFile(fileName);
        String cur = lex.getNextValue();
        while (cur != "") {
            System.out.println(cur);
            cur = lex.getNextValue();
        }
    }
}