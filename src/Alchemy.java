import java.io.IOException;

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
			} else if (arg.equals("-t")) {
				alchemist.printTerminalElements();
			} else if (arg.equals("-b")) {
				alchemist.printBasicElements();
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
					alchemist.printDerivedElements(arg2);
				} else if (arg1.equals("-b")) {
					alchemist.printBasicIngredientsFromElement(arg2);
				} else {
					System.out
							.println("/!\\ Secuencia de parámetros inválida.");
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

	private static void printHelpDesk() {
		System.out
				.println("--------------------------------------------------------------------------------");
		System.out
				.println("-------------------------------- MESA DE AYUDA ---------------------------------");
		System.out
				.println("--------------------------------------------------------------------------------");
		System.out
				.println("Juego en el que, partiendo de un grupo de elementos básicos, se deben");
		System.out.println("formar y descubrir los restantes.");
		System.out
				.println("Por ejemplo, si el usuario quiere intentar combinar el elementoA y el");
		System.out.println("elementoB la sintaxis es de la forma:");
		System.out.println("elementoA + elementoB");
		System.out.println();
		System.out.println(">>> COMANDOS ÚTILES:");
		System.out.println("	- '-e <elemento>':");
		System.out
				.println("		Imprime los elementos que se pueden formar usando <elemento>.");
		System.out.println();
		System.out.println("	- '-b <elemento>':");
		System.out
				.println("		Imprime aquellos elementos que generan <elemento>.");
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
		System.out
				.println("--------------------------------------------------------------------------------");
	}
}
