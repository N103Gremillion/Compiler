package lexer;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Lexer {

    List<Pattern> patterns;
    List<Token> tokens;
    String fileSource;
    int line;

    public Lexer (String file){
        this.line = 0;
        this.fileSource = file;
        this.tokens = new ArrayList<>();
        this.patterns = new ArrayList<>();
        addPatterns(this.patterns);
    }

    public void addPatterns(List<Pattern> patterns) {
        
    }

    public void tokenize(String file){
        try {
            int i;
            FileReader r = new FileReader(file);
            while((i = r.read()) != -1) {
                System.out.print((char) i);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }  
    }
    
}