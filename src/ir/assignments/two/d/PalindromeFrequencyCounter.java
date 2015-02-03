package ir.assignments.two.d;

import ir.assignments.three.Frequency;
import ir.assignments.two.a.Utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Collections;


public class PalindromeFrequencyCounter {
	/**
	 * This class should not be instantiated.
	 */
	private PalindromeFrequencyCounter() {}
	
	/**
	 * Takes the input list of words and processes it, returning a list
	 * of {@link Frequency}s.
	 * 
	 * This method expects a list of lowercase alphanumeric strings.
	 * If the input list is null, an empty list is returned.
	 * 
	 * There is one frequency in the output list for every 
	 * unique palindrome found in the original list. The frequency of each palindrome
	 * is equal to the number of times that palindrome occurs in the original list.
	 * 
	 * Palindromes can span sequential words in the input list.
	 * 
	 * The returned list is ordered by decreasing size, with tied palindromes sorted
	 * by frequency and further tied palindromes sorted alphabetically. 
	 * 
	 * The original list is not modified.
	 * 
	 * Example:
	 * 
	 * Given the input list of strings 
	 * ["do", "geese", "see", "god", "abba", "bat", "tab"]
	 * 
	 * The output list of palindromes should be 
	 * ["do geese see god:1", "bat tab:1", "abba:1"]
	 *  
	 * @param words A list of words.
	 * @return A list of palindrome frequencies, ordered by decreasing frequency.
	 */
	public static List<Frequency> computePalindromeFrequencies(ArrayList<String> words) {
//		/*
//		 * Logic Solution:
//		 * a) Initialize a HashMap that maps String -> Frequency
//		 * b) Iterate through the list of words (outerIndex)
//		 * 		Iterate through the words that are after the current word (innerIndex)
//		 * 			Do Palindrome check on outerIndex concatenated with innerIndex
//		 * 			Also keep this String so that new words from innerIndex will keep
//		 * 			getting concatenated to this String.
//		 *
//		 */

		int longestIndex = -1;
		ArrayList<Frequency> temp = new ArrayList<Frequency>();
		HashMap<String,Frequency> freqMap = new HashMap<String, Frequency>();
		for(int outerIndex = 0; outerIndex < words.size(); outerIndex++)
		{
			if (longestIndex != -1 && outerIndex > longestIndex)
			{
				temp = (ArrayList<Frequency>) computePalindromeFrequencies(new ArrayList<String>(words.subList(outerIndex,words.size())));
				break;
			}
			for (int innerIndex = words.size(); innerIndex > outerIndex && innerIndex > longestIndex; innerIndex--)
			{
				String word = String.join(" ",words.subList(outerIndex,innerIndex));
				if (isPalindrome(word.replaceAll(" ","")))
				{
					if (freqMap.containsKey(word))
					{
						freqMap.get(word).incrementFrequency();
					}
					else
					{
						freqMap.put(word, new Frequency(word,1));
					}
					longestIndex = innerIndex;
					break;
				}
			}
		}
		ArrayList<Frequency> returnFreq = new ArrayList<Frequency>(freqMap.values());
		returnFreq.addAll(temp);
		Collections.sort(returnFreq, new PalindromeComparator());

		return returnFreq;

	}

	/*
	 * Input: String
	 * Output: Boolean value, True if input String is a palindrome, False otherwise
	 */
	private static boolean isPalindrome(String str)
	{
		if (str.length() < 3)
		{
			return false;
		}
		for (int index = 0; index < str.length()/2; index++)
		{
			int strLength = str.length()-1;
			char firstIndex = str.charAt(index);
			char comparingIndex = str.charAt(strLength-index);
			if (firstIndex != comparingIndex)
			{
				return false;
			}
		}
		return true;
	}

//	private static HashMap<String,Frequency> combineMaps(HashMap<String,Frequency> map1,HashMap<String,Frequency> map2)
//	{
//		for (Entry<String,Frequency> entry : map2.entrySet())
//		{
//			if (map1.containsKey(entry.getKey()))
//			{
//				for (int i = 0; i < entry.getValue().getFrequency(); i++)
//					map1.get(entry.getKey()).incrementFrequency();
//			}
//			else
//			{
//				map1.put(entry.getKey(),entry.getValue());
//			}
//		}
//		return map1;
//	}
	
	/**
	 * Runs the 2-gram counter. The input should be the path to a text file.
	 * 
	 * @param args The first element should contain the path to a text file.
	 */
	public static void main(String[] args) {
		File file = new File(args[0]);
		ArrayList<String> words = Utilities.tokenizeFile(file);
		List<Frequency> frequencies = computePalindromeFrequencies(words);
		Utilities.printFrequencies(frequencies);

	}
}
