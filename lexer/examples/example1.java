package test;

import java.util.*;

public class Test {
    // Single-line comment
    private int number = 42;
    private double pi = 3.14;
    private boolean isJavaFun = true;
    private String message = "Hello, Lexer!";

    public static void main(String[] args) {
        System.out.println("Starting lexer test...");

        // Operators
        int x = 10 + 5;
        x -= 2;
        x *= 3;
        x /= 2;
        x %= 2;
        boolean check = (x > 5) && (x < 20);
        boolean notEqual = (x != 0);

        // Delimiters
        if (check) {
            System.out.println("Condition met!");
        } else {
            System.out.println("Condition not met!");
        }

        // Loops
        for (int i = 0; i < 5; i++) {
            System.out.println("Loop iteration: " + i);
        }

        // Lambda expression
        Runnable r = () -> System.out.println("Lambda executed");

        // Special characters
        String newline = "This is a newline:\n";
        String tabbed = "This is a tab:\tTabbed";

        // Multi-line comment
        /*
         * This is a multi-line comment
         * Used for testing purposes
         */

        // Identifiers
        int myVariable = 100;
        double anotherVariable = 50.5;

        r.run();
    }
}
