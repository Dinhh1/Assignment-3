/**
 * Dinh Ho 73374042,
 * David Chung 87654321
 * Anthony So 83689220
 *
 * Assignment 3
 * INF 141/CS 121
 */

package ir.assignments.three;

import java.util.Comparator;

/**
 * FreqComparator Class
 * Overrides compare method
 * This allows to use sort() function from Collection class
 * directly without the necessity to write a sorting algorithm
 */
public class FreqComparator implements Comparator<Frequency> {
    @Override
    public int compare(Frequency f1, Frequency f2) {
        int freq1 = f1.getFrequency();
        int freq2 = f2.getFrequency();
        if (freq1 < freq2)
            return 1;
        else if(freq1 == freq2)
            return f1.getText().compareTo(f2.getText());
        else
            return -1;
    }
}
