import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

import junit.framework.TestCase;

import org.junit.Test;

public class ParserTester extends TestCase {

	private static final String ELEMENTS_DEFAULT = "resources/elements.name";
	private static final String MAGIC_DEFAULT = "resources/elements.magic";

	private static final String TEST1_IN = "resources/test/test1.in";
	private static final String TEST1_OUT = "resources/test/test1.out";
	private static final String TEST1_ERR = "resources/test/test1.err";
	private static final String EXPECTED1_OUT = "resources/test/expected1.out";
	private static final String EXPECTED1_ERR = "resources/test/expected1.err";

	private static final String TEST2_IN = "resources/test/test2.in";
	private static final String TEST2_OUT = "resources/test/test2.out";
	private static final String TEST2_ERR = "resources/test/test2.err";
	private static final String EXPECTED2_OUT = "resources/test/expected2.out";
	private static final String EXPECTED2_ERR = "resources/test/expected2.err";
	private BufferedReader test1;
	private BufferedReader test2;

	@Test
	public void testInvalidCommands() {
		try {
			test1 = new BufferedReader(new FileReader(TEST1_IN));

			Alchemy.setAlchemist(ELEMENTS_DEFAULT, MAGIC_DEFAULT);

			PrintStream stdErr = new PrintStream(new BufferedOutputStream(
					new FileOutputStream(TEST1_ERR)));
			PrintStream stdOut = new PrintStream(new BufferedOutputStream(
					new FileOutputStream(TEST1_OUT)));
			PrintStream oldErr = System.err;
			PrintStream oldOut = System.out;
			System.setErr(stdErr);
			System.setOut(stdOut);

			Alchemy.parseInput(test1);

			stdErr.close();
			stdOut.close();

			System.setErr(oldErr);
			System.setOut(oldOut);

			assertTrue(fileEquals(TEST1_ERR, EXPECTED1_ERR));
			assertTrue(fileEquals(TEST1_OUT, EXPECTED1_OUT));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testValidCommands() {
		try {
			test2 = new BufferedReader(new FileReader(TEST2_IN));

			Alchemy.setAlchemist(ELEMENTS_DEFAULT, MAGIC_DEFAULT);

			PrintStream stdErr = new PrintStream(new BufferedOutputStream(
					new FileOutputStream(TEST2_ERR)));
			PrintStream stdOut = new PrintStream(new BufferedOutputStream(
					new FileOutputStream(TEST2_OUT)));

			PrintStream oldErr = System.err;
			PrintStream oldOut = System.out;
			System.setErr(stdErr);
			System.setOut(stdOut);

			Alchemy.parseInput(test2);

			stdErr.close();
			stdOut.close();

			System.setErr(oldErr);
			System.setOut(oldOut);

			assertTrue(fileEquals(TEST2_ERR, EXPECTED2_ERR));
			assertTrue(fileEquals(TEST2_OUT, EXPECTED2_OUT));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean fileEquals(String filename1, String filename2)
			throws IOException {
		BufferedReader file1 = new BufferedReader(new FileReader(filename1));
		BufferedReader file2 = new BufferedReader(new FileReader(filename2));

		String line1 = "", line2 = "";
		do {
			if (!line1.equals(line2))
				return false;
			line1 = file1.readLine();
			line2 = file2.readLine();
		} while (line1 != null && line2 != null);
		if (line1 == null && line2 == null)
			return true;
		return false;
	}

}
