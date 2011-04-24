import java.io.FileReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;

public class MagicLoader {

	public static HashMap<Potion, Element> load(List<Element> elementsList, String elementsFilename,
			String magicFilename) {
		HashMap<Potion, Element> magic = null;
		Reader reader;
		
		try {
			do {
				reader = new FileReader(magicFilename);
				MagicLexer lexer = new MagicLexer(reader);
				lexer.init(elementsList);
				magic = lexer.yylex();
			} while (magic == null);
		} catch (Exception e) {
			System.out.println("/!\\ Nombre del archivo de magia inválido.");
			System.exit(1);
		}
							
		return magic;	
	}
}
