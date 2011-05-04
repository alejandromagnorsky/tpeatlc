import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;

public class MagicLoader {

	public static HashMap<Potion, Element> load(List<Element> elementsList,
			String elementsFilename, String magicFilename) {
		HashMap<Potion, Element> magic = null;
		Reader reader;

		try {
			do {
				reader = new FileReader(magicFilename);
				MagicLexer lexer = new MagicLexer(reader);
				lexer.init(elementsList);
				magic = lexer.yylex();
			} while (magic == null);
		} catch (IllegalArgumentException e) {
			System.out.println("/!\\ Formato de archivo de magias inválido.");
			System.exit(1);
		} catch (IOException e) {
			System.out.println("/!\\ Archivo de magias inexistente.");
			System.exit(1);
		}

		return magic;
	}
}
