import java.io.FileReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;

public class MagicLoader {

	public static HashMap<Potion, Element> load(String elementsFilename,
			String magicFilename) {
		List<Element> elementsList = ElementLoader.load(elementsFilename);
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
			e.printStackTrace();
		}
		/*
		 * for(Entry<Potion, Element> entry: magic.entrySet())
		 * System.out.println(entry);
		 */
					
		return magic;	
	}
}
