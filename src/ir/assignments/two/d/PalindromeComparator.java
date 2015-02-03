package ir.assignments.two.d;

import ir.assignments.three.Frequency;
import java.util.Comparator;

/**
 * Created by dch on 1/22/15.
 */
/**
 * PalindromeComparator Class
 * Overrides compare method
 * This allows to use sort() function from Collection class
 * directly without the necessity to write a sorting algorithm
 */
public class PalindromeComparator implements Comparator<Frequency> {
    @Override
    public int compare(Frequency f1, Frequency f2) {
        int freq1 = f1.getText().length();
        int freq2 = f2.getText().length();
        if (freq1 < freq2)
        {
            return 1;
        }
        else if(freq1 == freq2)
        {
            if (f1.getFrequency() < f2.getFrequency())
            {
                return 1;
            }
            else if (f1.getFrequency() == f2.getFrequency())
            {
                return f1.getText().compareTo(f2.getText());
            }
            else
            {
                return -1;
            }
        }
        else
        {
            return -1;
        }
    }
}
