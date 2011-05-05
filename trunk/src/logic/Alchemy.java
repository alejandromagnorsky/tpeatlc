package logic;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

public class Alchemy {
	private static final String ELEMENTS_DEFAULT = "resources/elements.name";
	private static final String MAGIC_DEFAULT = "resources/elements.magic";
	private static String ELEMENTS_SRC = ELEMENTS_DEFAULT;
	private static String MAGIC_SRC = MAGIC_DEFAULT;
	private static Alchemist alchemist;

	public static void main(String[] args) throws IOException {

		printLogo();

		setAlchemist(ELEMENTS_SRC, MAGIC_SRC);
		boolean hFlag = false;
		boolean tFlag = false;
		boolean bFlag = false;
		boolean eFlag = false;
		boolean nmFlag = false;
		String bArg = "";
		String eArg = "";

		for (int i = 0; i < args.length; i++) {
			String arg1 = args[i];
			if (arg1.equals("-h"))
				hFlag = true;
			else if (arg1.equals("-t"))
				tFlag = true;
			else if (arg1.equals("-b") || arg1.equals("-e")
					|| arg1.equals("-n") || arg1.equals("-m")) {
				if (i + 1 >= args.length) {
					System.err
							.println("/!\\ Invalid argument sequence.");
					System.exit(1);
				}
				if (arg1.equals("-b")) {
					do {
						bArg = bArg + args[++i] + " ";
					} while (i + 1 < args.length
							&& args[i + 1].charAt(0) != '-');
					bArg = bArg.substring(0, bArg.length() - 1);
					bFlag = true;
				} else if (arg1.equals("-e")) {
					do {
						eArg = eArg + args[++i] + " ";
					} while (i + 1 < args.length
							&& args[i + 1].charAt(0) != '-');
					eArg = eArg.substring(0, eArg.length() - 1);
					eFlag = true;
				} else if (arg1.equals("-n")) {
					ELEMENTS_SRC = args[++i];
					nmFlag = true;
				} else {
					MAGIC_SRC = args[++i];
					nmFlag = true;
				}
			} else {
				System.err.println("/!\\ Invalid argument sequence.");
				System.exit(1);
			}
		}

		if (nmFlag)
			setAlchemist(ELEMENTS_SRC, MAGIC_SRC);
		if (hFlag) {
			printHelpDesk();
			printBasicElements();
			System.out
					.println("--------------------------------------------------------------------------------");
		}
		if (tFlag)
			printTerminalElements();
		if (bFlag)
			printBasicIngredientsFromElement(bArg);
		if (eFlag)
			printDerivedElements(eArg);

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		parseInput(reader);
		System.exit(0);
	}

	public static void parseInput(BufferedReader reader) throws IOException {
		AlchemyLexer lexer = new AlchemyLexer(reader);
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

	}

	public static void setAlchemist(String elements, String magic) {
		alchemist = new Alchemist(elements, magic);
	}

	public static void printLogo() {
		System.out.println("	 ___");
		System.out.println("	 -   -_, ,,      ,,                   -_   _");
		System.out.println("	(  ~/||  ||      ||                     |,- `");
		System.out
				.println("	(  / ||  ||  _-_ ||/\\\\  _-_  \\\\/\\\\/\\\\  ~||__))");
		System.out
				.println("	 \\/==||  || ||   || || || \\\\ || || ||  ~||__))");
		System.out.println("	 /_ _||  || ||   || || ||/   || || ||   |_ _,");
		System.out
				.println("	(  - \\\\, \\\\ \\\\,/ \\\\ |/ \\\\,/  \\\\ \\\\ \\\\  -' -");
		System.out.println("	                   _/                 ( _-_");
		System.out
				.println("==============================================================");
		System.out.println(randomMrCrowleyQuote());
		System.out.println(" 					Aleister Crowley");
		System.out
				.println("==============================================================");
	}

	private static String randomMrCrowleyQuote() {
		String quote;
		switch ((int) (Math.random() * 10)) {
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

	public static void printBasicElements() {
		List<Element> l = alchemist.getBasicElements();
		System.out.println("Basic elements:");
		for (Element e : l)
			System.out.println(e.getName());
	}

	public static void printBasicIngredientsFromElement(String e) {
		List<Element> l = alchemist.getBasicIngredientsFromElement(e);
		if (l == null) {
			System.out.println("/!\\ '" + e + "' does not exist.");
		} else {
			if (!l.isEmpty()) {
				Collections.sort(l);
				System.out
						.println("'" + e + "' is created by:");
				for (Element elem : l)
					System.out.println(elem.getName());
			} else
				System.out
						.println(e
								+ " cannot be created by basic elements.");
		}
	}

	public static void printDerivedElements(String e) {
		List<Element> l = alchemist.getDerivedElements(e);
		if (l == null) {
			System.out.println("/!\\ '" + e + "' does not exist.");
		} else {
			if (!l.isEmpty()) {
				Collections.sort(l);
				System.out
						.println("Using '"
								+ e + "' you can create:");
				for (Element elem : l)
					System.out.println(elem.getName());
			} else
				System.out.println(e + " is a terminal element.");
		}
	}

	public static void printTerminalElements() {
		List<Element> l = alchemist.getTerminalElements();
		System.out.println("Terminal elements:");
		for (Element e : l)
			System.out.println(e.getName());
	}

	private static void printHelpDesk() {
		System.out.println("--------------------------------------------------------------------------------");
		System.out.println("---------------------------------- HELP DESK -----------------------------------");
		System.out.println("--------------------------------------------------------------------------------");
		System.out.println("Given a set of basic elements, you must create and discover the rest of them.");
		System.out.println("For example, if one player wants to try to merge elementA with elementB,");
		System.out.println("sintaxis should look like:");
		System.out.println("	elementA + elementB.");
		System.out.println();
		System.out.println(">>> USEFUL COMMANDS:");
		System.out.println("	- '-e <element>':");
		System.out.println("		Prints <element>'s derived elements. ");
		System.out.println();
		System.out.println("	- '-b <element>':");
		System.out.println("		Prints those basic elements which can create <element>.");
		System.out.println();
		System.out.println("	- '-t':");
		System.out.println("		Prints terminal elements.");
		System.out.println();
		System.out.println("	- '-n <file>':");
		System.out.println("		Loads elements name list from <file>.");
		System.out.println();
		System.out.println("	- '-m <file>':");
		System.out.println("		Loads magic list from <file>.");
		System.out.println();
		System.out.println("	- '-h':");
		System.out.println("		Prints help desk.");
		System.out.println("--------------------------------------------------------------------------------");
	}
}
