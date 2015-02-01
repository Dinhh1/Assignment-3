package ir.assignments.three;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by dinhho on 1/31/15.
 */
public class Utils {

    /*
    This method will create our subdomain file
     */
    public static void writeSubdomain(HashMap<String, HashSet<String>> subdomains) {
        BufferedWriter output = null;
        try {
            File file = new File(System.getProperty("user.dir") + "/" + "Subdomains.txt");
            output = new BufferedWriter(new FileWriter(file));
            for (String subdomain : subdomains.keySet()) {
                output.write(subdomain + ", " + subdomains.get(subdomain).size() + "\n");
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
