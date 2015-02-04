/**
 * Dinh Ho 73374042,
 * David Chung 87654321
 * Anthony So 83689220
 *
 * Assignment 3
 * INF 141/CS 121
 */

package ir.assignments.three;

import java.io.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class Utils {
    // a set of stop words that we will omit during counting the words
    final private static HashSet<String> stopwords = new HashSet<String>(tokenizeFile(new File(System.getProperty("user.dir").concat("/stopwords.txt"))));

    /**
     * Tokenize File into tokens
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
     * Tokenize String into tokens
     *
     * @param input The file to read in and tokenize.
     * @return The list of tokens (words) from the input file, ordered by occurrence.
     */
    public static ArrayList<String> tokenizeString(String input) {
        ArrayList<String> words = new ArrayList<String>();
        String newLine = input;
        // remove all non-alphanumeric characters
        // source:
        // http://stackoverflow.com/questions/11796985/java-regular-expression-to-remove-all-non-alphanumeric-characters-except-spaces
        newLine = newLine.replaceAll("[^A-Za-z0-9\\s.-]", "").toLowerCase();
        StringTokenizer st = new StringTokenizer(newLine, " -.\t\n\r\f");
        while (st.hasMoreTokens()) {
            String nextToken = st.nextToken();
            if (!nextToken.equals("") && !stopwords.contains(nextToken))
                words.add(nextToken);
        }
        return words;
    }

    /**
     * compute the aggregate word frequency of the entire crawl
     *
     * @param words The list of words to count
     * @return a map<String, Frequency> of the aggregate word counted so far
     */
    public static HashMap<String, Frequency> computeWordFrequency(ArrayList<String> words,
                                                                  HashMap<String, Frequency> freqMap) {
        if (freqMap == null)
            freqMap = new HashMap<String, Frequency>();
        if (words== null || words.size() == 0)
            return freqMap;
        for (String word : words) {
            if (freqMap.containsKey(word))
                freqMap.get(word).incrementFrequency();
            else
                freqMap.put(word, new Frequency(word, 1));
        }
        return freqMap;

    }

    /**
     * Write the top 500 words found during the crawl and their frequency
     *
     * @param wordList The file to read in and tokenize.
     * @return void
     */
    public static void writeCommonWords(HashMap<String,Frequency> wordList)
    {
        BufferedWriter output = null;
        try {
            File file = new File(System.getProperty("user.dir") + "/" + "CommonWords.txt");
            output = new BufferedWriter(new FileWriter(file));

            ArrayList<Frequency> f = new ArrayList<Frequency>(wordList.values());
            Collections.sort(f, new FreqComparator());
            int size = f.size();
            if (size > 499)
                size = 499;
            for (int i = 0; i < size; i++) {
                output.write(f.get(i) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (IOException e) {
                System.out.println("Fail to close CommonWords.txt");
            }
        }
    }

    /**
     * Write each subdomain in alphabetical order and how many websites we crawled on that domain
     *
     * @param subDomains The subdomain map to write
     * @return void
     */
    public static void writeSubDomain(HashMap<String, Frequency> subDomains) {
        BufferedWriter output = null;
        try {
            File file = new File(System.getProperty("user.dir") + "/" + "Subdomains.txt");
            output = new BufferedWriter(new FileWriter(file));
            ArrayList<String> keySet = new ArrayList<String>(subDomains.keySet());
            Collections.sort(keySet,  new Comparator<String>() {
                public int compare(String s1, String s2)
                {
                    return s1.toString().compareTo(s2.toString());
                }
            });

            for (String subDomain : keySet) {
                output.write(subDomain + ", " + subDomains.get(subDomain).getFrequency() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (IOException e) {
                System.out.println("Fail to close subdomains.txt");
            }
        }
    }

    /**
     * Write the list of visited url
     *
     * @param urls The subdomain map to write
     * @return void
     */
    public static void writeCollectionList(Collection<String> urls) {
        BufferedWriter output = null;
        try {
            File file = new File(System.getProperty("user.dir") + "/" + "crawled_sites.txt");
            output = new BufferedWriter(new FileWriter(file));
            for (String site : urls) {
                output.write(site + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (IOException e) {
                System.out.println("Fail to close urls.txt");
            }
        }
    }
}
