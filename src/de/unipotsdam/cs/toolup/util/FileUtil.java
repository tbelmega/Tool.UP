package de.unipotsdam.cs.toolup.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class FileUtil {
	
	private static final String END_OF_FILE = "\\A";
	public static final String UTF_8 = StandardCharsets.UTF_8.toString();
	
	/**
	 * Returns the content of a text file as String.
	 * @param f
	 * @return
	 * @throws IOException
	 */
	public static String readFile (File f) throws IOException {
		return readFile(new FileInputStream(f));
	}
	
	/**
	 * Returns the content of a text file as String.
	 * @param s path to an existing text file
	 * @return
	 * @throws IOException
	 */
	public static String readFile (String s) throws IOException {
		return readFile(new File(s));
	}

	/**
	 * Returns the content of a text file as String.
	 * @param i
	 * @return
	 * @throws IOException
	 */
	public static String readFile(InputStream i) throws IOException {		
		Scanner scan = new Scanner(i,UTF_8);
		scan.useDelimiter(END_OF_FILE);
        String content = scan.next();
        scan.close();
		return content;	
	}
}
