package src;

import java.io.IOException;

public class Ex {
	public static void main(String[] args) throws IOException{
		Lexer lexer = new Lexer(System.in);
		lexer.yylex();
	}
}
