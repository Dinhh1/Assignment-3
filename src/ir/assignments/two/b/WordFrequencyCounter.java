package ir.assignments.two.b;

import ir.assignments.two.a.Frequency;
import ir.assignments.two.a.Utilities;
import ir.assignments.two.a.FreqComparator;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * Counts the total number of words and their frequencies in a text file.
 */
public final class WordFrequencyCounter {
	/**
	 * This class should not be instantiated.
	 */
	private WordFrequencyCounter() {}
	
	/**
	 * Takes the input list of words and processes it, returning a list
	 * of {@link Frequency}s.
	 * 
	 * This method expects a list of lowercase alphanumeric strings.
	 * If the input list is null, an empty list is returned.
	 * 
	 * There is one frequency in the output list for every 
	 * unique word in the original list. The frequency of each word
	 * is equal to the number of times that word occurs in the original list. 
	 * 
	 * The returned list is ordered by decreasing frequency, with tied words sorted
	 * alphabetically.
	 * 
	 * The original list is not modified.
	 * 
	 * Example:
	 * 
	 * Given the input list of strings 
	 * ["this", "sentence", "repeats", "the", "word", "sentence"]
	 * 
	 * The output list of frequencies should be 
	 * ["sentence:2", "the:1", "this:1", "repeats:1",  "word:1"]
	 *  
	 * @param words A list of words.
	 * @return A list of word frequencies, ordered by decreasing frequency.
	 *
	 */
	public static List<Frequency> computeWordFrequencies(List<String> words) {
	   /*
		* Logic for the solution:
		* a) Sort the list of words
		* b) iterate through each word by order,
		* 		if a word is the same as the previous word:
		* 			then increment word count
		* 		else:
		*			then add new word
 		*/			ArrayList<Frequency> freqList = new ArrayList<Frequency>();

		Collections.sort(words);

		Frequency freq = new Frequency(words.get(0));
		freqList.add(freq);

		for (String word : words)
		{
			if (word.equals(freq.getText()))
			{
				freqList.get(freqList.indexOf(freq)).incrementFrequency();
			}
			else
			{
				freq = new Frequency(word,1);
				freqList.add(freq);
			}
		}

		//Sort Frequency List
		Collections.sort(freqList, new FreqComparator());

		return freqList;
	}
	
	/**
	 * Runs the word frequency counter. The input should be the path to a text file.
	 * 
	 * @param args The first element should contain the path to a text file.
	 */
	public static void main(String[] args) {
		File file = new File(args[0]);
		List<String> words = Utilities.tokenizeFile(file);
		List<Frequency> frequencies = computeWordFrequencies(words);
		Utilities.printFrequencies(frequencies);
	}
}
