package ir.assignments.three;

import ir.assignments.two.a.FreqComparator;
import ir.assignments.two.a.Frequency;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by dinhho on 1/31/15.
 */
public class Utils {

    public static void writeCommonWords(HashMap<String,Frequency> wordList)
    {


        BufferedWriter output = null;
        try {
            File file = new File(System.getProperty("user.dir") + "/" + "CommonWords.txt");
            output = new BufferedWriter(new FileWriter(file));

            ArrayList<Frequency> f = new ArrayList<Frequency>(wordList.values());
            Collections.sort(f, new FreqComparator());
            for (int i = 0; i < 499; i++) {
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
}
