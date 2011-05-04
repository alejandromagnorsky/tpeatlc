import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class Alchemy {
	private static final String ELEMENTS_DEFAULT = "resources/myelements.name";
	private static final String MAGIC_DEFAULT = "resources/myelements.magic";
	private static String ELEMENTS_SRC = ELEMENTS_DEFAULT;
	private static String MAGIC_SRC = MAGIC_DEFAULT;
	private static Alchemist alchemist;
	
	public static void main(String[] args) throws IOException {
		
		printLogo();
		
		alchemist = new Alchemist(ELEMENTS_SRC, MAGIC_SRC);
		boolean hFlag = false;
		boolean tFlag = false;
		boolean bFlag = false;
		boolean eFlag = false;
		boolean nmFlag = false;
		String bArg = null;
		String eArg = null;
		
		for (int i=0; i < args.length; i++){
			String arg1 = args[i];
			if (arg1.equals("-h"))
				hFlag = true;
			else if (arg1.equals("-t"))
				tFlag = true;				
			else if (arg1.equals("-b") || arg1.equals("-e") || arg1.equals("-n") || arg1.equals("-m")){
				if (i+1 >= args.length){
					System.out.println("/!\\ Secuencia de argumentos inválida.");
					return;
				}
				if (arg1.equals("-b")){
					bArg = args[++i];
					bFlag = true;
				} else if (arg1.equals("-e")){
					eArg = args[++i];
					eFlag = true;
				} else if (arg1.equals("-n")){
					ELEMENTS_SRC = args[++i];
					nmFlag = true;
				} else {
					MAGIC_SRC = args[++i];
					nmFlag = true;
				}
			} else {
				System.out.println("/!\\ Secuencia de argumentos inválida.");
				return;
			}
		}

		if (nmFlag)
			alchemist = new Alchemist(ELEMENTS_SRC, MAGIC_SRC);
		if (hFlag){
			printHelpDesk();
			printBasicElements();
			System.out.println("--------------------------------------------------------------------------------");
		}
		if (tFlag)
			printTerminalElements();
		if (bFlag)
			printBasicIngredientsFromElement(bArg);
		if (eFlag)
			printDerivedElements(eArg);
		
		AlchemyLexer lexer = new AlchemyLexer(System.in);
		String yytext = null, element1, element2;
		Element result;
		yytext = lexer.yylex();
		if (yytext == null)
			System.exit(0);
		do {
			element1 = yytext.substring(0, yytext.indexOf("+"));
			element2 = yytext.substring(yytext.indexOf("+") + 1);
			result = alchemist.merge(element1, element2);
			if (result == null)
				System.out.println("???");
			else
				System.out.println(result.getName());
			yytext = lexer.yylex();
		} while (yytext != null);
		System.exit(0);
	}
	
	public static void printLogo(){
		System.out.println("	 ___");
		System.out.println("	 -   -_, ,,      ,,                   -_   _");
		System.out.println("	(  ~/||  ||      ||                     |,- `");
		System.out.println("	(  / ||  ||  _-_ ||/\\\\  _-_  \\\\/\\\\/\\\\  ~||__))");
		System.out.println("	 \\/==||  || ||   || || || \\\\ || || ||  ~||__))");
		System.out.println("	 /_ _||  || ||   || || ||/   || || ||   |_ _,");
		System.out.println("	(  - \\\\, \\\\ \\\\,/ \\\\ |/ \\\\,/  \\\\ \\\\ \\\\  -' -");
		System.out.println("	                   _/                 ( _-_");
		System.out.println("==============================================================");
		System.out.println(randomMrCrowleyQuote());
		System.out.println(" 					Aleister Crowley");
		System.out.println("==============================================================");
	}
	
	private static String randomMrCrowleyQuote(){
		String quote;
		switch((int)(Math.random()*10)){
		case 0:
			quote = "\"Science is always discovering odd scraps of magical wisdom\nand making a tremendous fuss about its cleverness.\"";
			break;
		case 1:
			quote = "\"The people who have really made history are the martyrs.\"";
			break;
		case 2:
			quote = "\"The pious pretense that evil does not exist only makes it\nvague, enormous and menacing.\"";
			break;
		case 3:
			quote = "\"One would go mad if one took the Bible seriously; but to\ntake it seriously one must be already mad.\"";
			break;
		case 4:
			quote = "\"The sin which is unpardonable is knowingly and wilfully to\nreject truth, to fear knowledge lest that knowledge\npander not to thy prejudices.\"";
			break;
		default:
			quote = "\"Magick is the science and art of causing change to occur\nin conformity with will.\"";
			break;
		}
		return quote;
	}
	
	public static void printBasicElements(){
		List<Element> l = alchemist.getBasicElements();
		System.out.println("Elementos básicos:");
		for (Element e : l)
			System.out.println(e.getName());
	}
	
	public static void printBasicIngredientsFromElement(String e){
		List<Element> l = alchemist.getBasicIngredientsFromElement(e);
		if (l == null){
			System.out.println("/!\\ No existe el elemento '" + e + "'.");
		} else {
			if (!l.isEmpty()){
				Collections.sort(l);
				System.out.println("Elementos básicos que forman '" + e + "':");
				for (Element elem : l)
					System.out.println(elem.getName());
			} else
				System.out.println(e + " es básico.");
		}
	}
	
	public static void printDerivedElements(String e){
		List<Element> l = alchemist.getDerivedElements(e);
		if (l == null){
			System.out.println("/!\\ No existe el elemento '" + e + "'.");
		} else {
			if (!l.isEmpty()){
				Collections.sort(l);
				System.out.println("Elementos que pueden ser formados a partir de '" + e + "':");
				for (Element elem : l)
					System.out.println(elem.getName());
			} else
				System.out.println(e + " es terminal.");
		}		
	}
	
	public static void printTerminalElements(){
		List<Element> l = alchemist.getTerminalElements();
		System.out.println("Elementos terminales:");
		for (Element e : l)
			System.out.println(e.getName());
	}
	
	private static void printHelpDesk() {
		System.out.println("--------------------------------------------------------------------------------");
		System.out.println("-------------------------------- MESA DE AYUDA ---------------------------------");
		System.out.println("--------------------------------------------------------------------------------");
		System.out.println("Juego en el que, partiendo de un grupo de elementos básicos, se deben");
		System.out.println("formar y descubrir los restantes.");
		System.out.println("Por ejemplo, si el usuario quiere intentar combinar el elementoA y el");
		System.out.println("elementoB la sintaxis es de la forma:");
		System.out.println("elementoA + elementoB");
		System.out.println();
		System.out.println(">>> COMANDOS ÚTILES:");
		System.out.println("	- '-e <elemento>':");
		System.out.println("		Imprime los elementos que se pueden formar usando <elemento>.");
		System.out.println();
		System.out.println("	- '-b <elemento>':");
		System.out.println("		Imprime aquellos elementos que generan <elemento>.");
		System.out.println();
		System.out.println("	- '-t':");
		System.out.println("		Imprime todos los elementos terminales.");
		System.out.println();
		System.out.println("	- '-n <archivo>':");
		System.out.println("		Los elementos son cargados desde <archivo>.");
		System.out.println();
		System.out.println("	- '-m <archivo>':");
		System.out.println("		Las magias son cargadas desde <archivo>.");
		System.out.println();
		System.out.println("	- '-h':");
		System.out.println("		Imprime la mesa de ayuda.");
		System.out.println("--------------------------------------------------------------------------------");
	}
}