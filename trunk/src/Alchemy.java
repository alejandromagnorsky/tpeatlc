import java.io.IOException;

public class Alchemy {
	public static void main(String[] args) throws IOException{
		Lexer lexer = new Lexer(System.in);
		while(true){
			String yytext = lexer.yylex();
			if(yytext != null)
				System.out.println(yytext);
		}
	}
}
