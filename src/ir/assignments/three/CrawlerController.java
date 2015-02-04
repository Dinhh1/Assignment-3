/**
 * Dinh Ho 73374042,
 * David Chung 87654321
 * Anthony So 83689220
 *
 * Assignment 3
 * INF 141/CS 121
 */

package ir.assignments.three;

import com.sun.tools.javac.util.Pair;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;



public class CrawlerController {
    public CrawlerController() {}


    private static final String USER_AGENT = "UCI Inf141-CS121 crawler 73374042 87654321 83689220";
    private static final int CRAWLER_DELAY = 300;
    private static final int NUMBER_OF_CRAWLERS = 100;

    private static HashMap<String, Frequency> domainMap = new HashMap<String, Frequency>();
    private static HashSet<String> urls = new HashSet<String>();
    private static Pair<String, Integer> longestPage = new Pair<String, Integer>("", 0);
    private static HashMap<String, Frequency> wordList = new HashMap<String, Frequency>();

    /**
     * count the word frequency of the entire page
     *
     * @param text content of the current visited page
     * @return void
     */
    public static synchronized void addTextToWordList(String text) {
        wordList = Utils.computeWordFrequency(Utils.tokenizeString(text), wordList);
    }

    /**
     * checks whether or not this is currently the longest page we've visited
     *
     * @param length the length of the page
     * @param url the url of the page
     * @return void
     */
    public static synchronized void checkLongestPage(int length, String url) {
        if (length > longestPage.snd)
            CrawlerController.longestPage = new Pair<String, Integer>(url, length);
    }

    /**
     * Increment how many sites we've seen for this particular subdomain
     *
     * @param subDomain subdomain to increment
     * @return void
     */
    public static synchronized void incrementSubDomain(String subDomain) {
        if (domainMap.containsKey(subDomain)) {
            domainMap.get(subDomain).incrementFrequency();
        } else {
            domainMap.put(subDomain, new Frequency(subDomain, 1));
            System.out.println("added new domain: " + subDomain);
        }
    }

    /**
     * add a new url to our visited set
     *
     * @param url url to add
     * @return void
     */
    public static synchronized void addURL(String url) {
        urls.add(url);
    }

    /**
     * checks if we've visisted a particular URL already
     *
     * @param url url to check
     * @return boolean
     */
    public static synchronized boolean didVisitURL(String url) {
        return urls.contains(url);
    }


    public static Collection<String> getURLS() {
        return urls;
    }

    public static HashMap<String, Frequency> getDomainMap() {
        return domainMap;
    }

    public static Pair<String, Integer> getLongestPage() {
        return longestPage;
    }

    public static HashMap<String, Frequency> getWordList() {
        return wordList;
    }

    /**
     * configure, initialize and start our webcrawler with the provided seed
     *
     * @return void
     */
    public static void init(String urlSeed) throws Exception {
        String crawlStorageFolder = System.getProperty("user.dir").concat("/data/crawl/root");
        // configure our crawler
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);
        config.setPolitenessDelay(CRAWLER_DELAY);
        config.setUserAgentString(USER_AGENT);
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
        // add seed and start
        controller.addSeed(urlSeed);
        controller.start(ICSWebCrawler.class, NUMBER_OF_CRAWLERS);
    }



}
