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
		return CrawlerController.urls;
	}

	public static void main(String[] args)
	{
		long startTime = System.currentTimeMillis();
		Collection<String> urls = crawl("http://www.ics.uci.edu");
		long stopTime = System.currentTimeMillis();
		long runTime = stopTime - startTime;
		System.out.println("Run time: " + TimeUnit.MILLISECONDS.toMinutes(runTime));
	}

}
