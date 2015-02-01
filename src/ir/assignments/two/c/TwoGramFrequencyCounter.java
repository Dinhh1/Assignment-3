package ir.assignments.two.c;

import ir.assignments.two.a.FreqComparator;
import ir.assignments.two.a.Frequency;
import ir.assignments.two.a.Utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;

/**
 * Count the total number of 2-grams and their frequencies in a text file.
 */
public final class TwoGramFrequencyCounter {
	/**
	 * This class should not be instantiated.
	 */
	private TwoGramFrequencyCounter() {}
	
	/**
	 * Takes the input list of words and processes it, returning a list
	 * of {@link Frequency}s.
	 * 
	 * This method expects a list of lowercase alphanumeric strings.
	 * If the input list is null, an empty list is returned.
	 * 
	 * There is one frequency in the output list for every 
	 * unique 2-gram in the original list. The frequency of each 2-grams
	 * is equal to the number of times that two-gram occurs in the original list. 
	 * 
	 * The returned list is ordered by decreasing frequency, with tied 2-grams sorted
	 * alphabetically. 
	 * 
	 * 
	 * 
	 * Example:
	 * 
	 * Given the input list of strings 
	 * ["you", "think", "you", "know", "how", "you", "think"]
	 * 
	 * The output list of 2-gram frequencies should be 
	 * ["you think:2", "how you:1", "know how:1", "think you:1", "you know:1"]
	 *  
	 * @param words A list of words.
	 * @return A list of two gram frequencies, ordered by decreasing frequency.
	 */
	public static List<Frequency> computeTwoGramFrequencies(ArrayList<String> words) {
		/*
		* Logic for the solution:
		* a) Initialize a HashMap that maps String -> Frequency
		* b) Iterate through each word
		* 		If Key does not exist, where key=current word + next word
		* 			Add the word and the next word as a key into HashMap
		* 		else:
		*	 		increment the Frequency-value count
		 */
		HashMap<String,Frequency> freqMap = new HashMap<String, Frequency>();
		for (int index = 0; index < words.size()-1; index++)
		{
			String twoGram = words.get(index).concat(" ".concat(words.get(index+1)));
			if (freqMap.containsKey(twoGram))
			{
				freqMap.get(twoGram).incrementFrequency();
			}
			else
			{
				freqMap.put(twoGram, new Frequency(twoGram,1));
			}
		}
		ArrayList<Frequency> returnFreq = new ArrayList<Frequency>(freqMap.values());
		Collections.sort(returnFreq, new FreqComparator());
		return returnFreq;
	}
	
	/**
	 * Runs the 2-gram counter. The input should be the path to a text file.
	 * 
	 * @param args The first element should contain the path to a text file.
	 */
	public static void main(String[] args) {
		File file = new File(args[0]);
		ArrayList<String> words = Utilities.tokenizeFile(file);
		List<Frequency> frequencies = computeTwoGramFrequencies(words);
		Utilities.printFrequencies(frequencies);
	}
}
