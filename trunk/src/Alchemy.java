import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class Alchemy {
	private static final String ELEMENTS_DEFAULT = "resources/myelements.name";
	private static final String MAGICK_DEFAULT = "resources/myelements.magic";

	// |-> Si, con K, no leíste a Aleister Crowley?

	public static void main(String[] args) throws IOException {
		Alchemist alchemist;

		if (args.length == 1) {
			alchemist = new Alchemist(ELEMENTS_DEFAULT, MAGICK_DEFAULT);
			String arg = args[0];
			if (arg.equals("-h")) {
				printHelpDesk();
				printBasicElements(alchemist);
				System.out.println("--------------------------------------------------------------------------------");
			} else if (arg.equals("-t")) {
				printTerminalElements(alchemist);
			} else if (arg.equals("-b")) {
				printBasicElements(alchemist);
			} else {
				System.out.println("/!\\ Secuencia de parámetros inválida.");
			}
			return;
		} else if (args.length == 2) {
			String arg1 = args[0];
			String arg2 = args[1];
			if (arg1.equals("-n") || arg1.equals("-m")) {
				if (arg1.equals("-n")) {
					alchemist = new Alchemist(arg2, MAGICK_DEFAULT);
				} else {
					alchemist = new Alchemist(ELEMENTS_DEFAULT, arg2);
				}
			} else {
				alchemist = new Alchemist(ELEMENTS_DEFAULT, MAGICK_DEFAULT);
				if (arg1.equals("-e")) {
					printDerivedElements(arg2, alchemist);
				} else if (arg1.equals("-b")) {
					printBasicIngredientsFromElement(arg2, alchemist);
				} else {
					System.out.println("/!\\ Secuencia de parámetros inválida.");
				}
				return;
			}
		} else if (args.length == 0) {
			alchemist = new Alchemist(ELEMENTS_DEFAULT, MAGICK_DEFAULT);
		} else {
			System.out.println("/!\\ Secuencia de parámetros inválida.");
			return;
		}

		AlchemyLexer lexer = new AlchemyLexer(System.in);
		String yytext = null, element1, element2;
		Element result;
		yytext = lexer.yylex();
		if (yytext == null)
			return;
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
	
	public static void printBasicElements(Alchemist a){
		List<Element> l = a.getBasicElements();
		for (Element e : l)
			System.out.println(e.getName());
	}
	
	public static void printBasicIngredientsFromElement(String e, Alchemist a){
		List<Element> l = a.getBasicIngredientsFromElement(e);
		if (l == null){
			System.out.println("/!\\ No existe el elemento '" + e + "'.");
		} else {
			if (!l.isEmpty()){
				Collections.sort(l);
				for (Element elem : l)
					System.out.println(elem.getName());
			} else
				System.out.println(e + " es básico.");
		}
	}
	
	public static void printDerivedElements(String e, Alchemist a){
		List<Element> l = a.getDerivedElements(e);
		if (l == null){
			System.out.println("/!\\ No existe el elemento '" + e + "'.");
		} else {
			if (!l.isEmpty()){
				Collections.sort(l);
				for (Element elem : l)
					System.out.println(elem.getName());
			} else
				System.out.println(e + " es terminal.");
		}		
	}
	
	public static void printTerminalElements(Alchemist a){
		List<Element> l = a.getTerminalElements();
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
		System.out.println("	- '-b':");
		System.out.println("		Imprime todos los elementos básicos.");
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
