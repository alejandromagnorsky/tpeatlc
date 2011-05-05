import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class ElementsLoader {

	public static List<Element> load(String filename) {
		Reader reader;
		List<Element> elements = new ArrayList<Element>();
		int i = 1;
		try {
			reader = new FileReader(filename);
			ElementsLexer lexer = new ElementsLexer(reader);
			String yytext = null;
			do {
				yytext = lexer.yylex();
				if (yytext != "EOF") {
					elements.add(new Element(i, yytext));
					i++;
				}
			} while (!yytext.equals("EOF"));
		} catch (IllegalArgumentException e) {
			System.err.println("/!\\ Formato de archivo de nombres inválido.");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("/!\\ Archivo de nombres inexistente.");
			System.exit(1);
		}
		return elements;
	}
}
