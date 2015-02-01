package ir.assignments.two.a;

import java.io.*;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.List;

/**
 * A collection of utility methods for text processing.
 */
public class Utilities {
	/**
	 * Reads the input text file and splits it into alphanumeric tokens.
	 * Returns an ArrayList of these tokens, ordered according to their
	 * occurrence in the original text file.
	 * 
	 * Non-alphanumeric characters delineate tokens, and are discarded.
	 *
	 * Words are also normalized to lower case. 
	 * 
	 * Example:
	 * 
	 * Given this input string
	 * "An input string, this is! (or is it?)"
	 * 
	 * The output list of strings should be
	 * ["an", "input", "string", "this", "is", "or", "is", "it"]
	 * 
	 * @param input The file to read in and tokenize.
	 * @return The list of tokens (words) from the input file, ordered by occurrence.
	 */
	public static ArrayList<String> tokenizeFile(File input) {
		//Initialize list of string (tokens)
		ArrayList<String> tokens = new ArrayList<String>();
		try {
			FileInputStream fstream = new FileInputStream(input);
			BufferedReader reader = new BufferedReader(new InputStreamReader(fstream));
			String newLine = "";
			while((newLine = reader.readLine()) != null)
			{
				/**
				 * Regular expression:
				 * - splits by non-alphanumeric characters
				 * - Splits by Punctuation(\p{Punct}: One of !"#$%&'()*+,-./:;<=>?@[\]^_`{|}~ from Oracle Pattern documentations
				 * - Splits by Whitespaces (\s)
				 *
				**/
				String[] lineToken = newLine.split("[[^a-zA-z]\\p{Punct}\\s]+");
				for (String str : lineToken)
				{
					if (!str.equals(""))
						tokens.add(str.toLowerCase());
				}
			}
			//Close reader
			reader.close();

		} catch (FileNotFoundException fnfe) {
			System.out.println("ERROR: File was not found.");
		} catch(Exception e) {
			System.out.println("An Error Occurred.");

		}

		return tokens;
	}

	/**
	 * Takes a list of {@link Frequency}s and prints it to standard out. It also
	 * prints out the total number of items, and the total number of unique items.
	 *
	 * Example one:
	 *
	 * Given the input list of word frequencies
	 * ["sentence:2", "the:1", "this:1", "repeats:1",  "word:1"]
	 *
	 * The following should be printed to standard out
	 *
	 * Total item count: 6
	 * Unique item count: 5
	 *
	 * sentence	2
	 * the		1
	 * this		1
	 * repeats	1
	 * word		1
	 *
	 *
	 * Example two:
	 *
	 * Given the input list of 2-gram frequencies
	 * ["you think:2", "how you:1", "know how:1", "think you:1", "you know:1"]
	 *
	 * The following should be printed to standard out
	 *
	 * Total 2-gram count: 6
	 * Unique 2-gram count: 5
	 *
	 * you think	2
	 * how you		1
	 * know how		1
	 * think you	1
	 * you know		1
	 *
	 * @param frequencies A list of frequencies.
	 */
	public static void printFrequencies(List<Frequency> frequencies) {
		//Total item count
		int totalCount = 0;
		for (Frequency freq : frequencies) {
			totalCount += freq.getFrequency();
		}
		System.out.print("Total item count:");
		System.out.println(totalCount);
		//Unique item count
		System.out.print("Unique item count: ");
		System.out.println(frequencies.size());
		for (Frequency freq : frequencies)
		{
			System.out.println(freq.toString());
		}
	}
}