import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class ElementLoader {

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

		} catch (Exception e) {
			System.out.println("/!\\ Nombre del archivo de elementos inválido.");
			System.exit(1);
		}
		return elements;
	}
}
