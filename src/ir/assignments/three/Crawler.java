package ir.assignments.three;

import edu.uci.ics.crawler4j.crawler.WebCrawler;
import ir.assignments.two.a.FreqComparator;
import ir.assignments.two.a.Frequency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
		System.out.println("Starting...");
		Collection<String> urls = crawl("http://archive.ics.uci.edu/ml/datasets.html");
		long stopTime = System.currentTimeMillis();
		long runTime = stopTime - startTime;
		System.out.println("Run time: " + TimeUnit.MILLISECONDS.toSeconds(runTime));
		System.out.println("Number of unique pages: " + urls.size());
		System.out.println("Longest page: " + CrawlerController.longestPage.toString());
				// now lets write the subdomain to text
		Utils.writeSubdomain(CrawlerController.domainMap);
		Utils.writeCommonWords(CrawlerController.wordList);
	}

}
