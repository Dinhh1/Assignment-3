/**
 * Dinh Ho 73374042,
 * David Chung 87654321
 * Anthony So 83689220
 *
 * Assignment 3
 * INF 141/CS 121
 */

package ir.assignments.three;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

public class Crawler {

	/**
	 * This method is for testing purposes only. It does not need to be used
	 * to answer any of the questions in the assignment. However, it must
	 * function as specified so that your crawler can be verified programatically.
	 * 
	 * This methods performs a crawl starting at the specified seed URL. Returns a
	 * collection containing all URLs visited during the crawl.
	 */
	public static Collection<String> crawl(String seedURL) {
		try {
			CrawlerController.init(seedURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CrawlerController.getURLS();
	}

	public static void main(String[] args)
	{
		long startTime = System.currentTimeMillis();
		System.out.println("Starting...");
		Collection<String> urls = crawl("http://www.ics.uci.edu");
		long stopTime = System.currentTimeMillis();
		long runTime = stopTime - startTime;
		System.out.println("Run time in seconds: " + TimeUnit.MILLISECONDS.toSeconds(runTime) + " seconds");
		System.out.println("Run time in minutes : " + TimeUnit.MILLISECONDS.toMinutes(runTime) + " minutes");
		System.out.println("Run time in hours : " + TimeUnit.MILLISECONDS.toHours(runTime) + " hours");
		System.out.println("Number of unique pages: " + urls.size());
		System.out.println("Longest page: " + CrawlerController.getLongestPage().toString());
		Utils.writeSubDomain(CrawlerController.getDomainMap());
		Utils.writeCommonWords(CrawlerController.getWordList());
		Utils.writeCollectionList(urls);
	}

}
