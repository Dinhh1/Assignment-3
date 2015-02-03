package ir.assignments.three;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.io.*;

/**
 * Created by dinhho on 1/31/15.
 */
public class Utils {

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

    public static ArrayList<String> tokenizeString(String text)
    {
        //Initialize list of string (tokens)
        ArrayList<String> tokens = new ArrayList<String>();
        String[] lineToken = text.split("[[^a-zA-z]\\p{Punct}\\s]+");

        for (String str : lineToken)
        {
            if (!str.equals("") && !CrawlerController.stopwords.contains(str))
                tokens.add(str.toLowerCase());
        }

        return tokens;
    }

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
    /*
    This method will create our subdomain file
     */
    public static void writeSubdomain(HashMap<String, Frequency> subdomains) {
        BufferedWriter output = null;
        try {
            File file = new File(System.getProperty("user.dir") + "/" + "Subdomains.txt");
            output = new BufferedWriter(new FileWriter(file));
            ArrayList<String> keySet = new ArrayList<String>(subdomains.keySet());
            Collections.sort(keySet,  new Comparator<String>() {
                public int compare(String s1, String s2)
                {
                    return s1.toString().compareTo(s2.toString());
                }
            });

            for (String subdomain : keySet) {
                output.write(subdomain + ", " + subdomains.get(subdomain).getFrequency() + "\n");
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
