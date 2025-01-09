
import lexer.Lexer;


public class main{
    public static void main(String[] args){
        Lexer lex = new Lexer();
        lex.setFile("./lexer/examples/example1.java");
        lex.tokenize();
        System.out.println(lex.toString());
    }
}