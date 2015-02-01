package ir.assignments.three;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import ir.assignments.two.a.Frequency;

import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;

import java.io.File;

/**
 * Created by dinhho on 1/31/15.
 */
public class CrawlerController {
    public CrawlerController() {}


    private static final String USER_AGENT = "UCI Inf141-CS121 crawler 12345678 87654321";
    private static final int CRAWLER_DELAY = 300;
    private static final int NUMBER_OF_CRAWLERS = 500;


    //Key: Doman, Value: Sub-Domain Set
    public static HashMap<String, HashSet<String> > domainMap = new HashMap<String, HashSet<String>>();
    public static HashSet<String> urls = new HashSet<String>();
    public static HashSet<String> stopwords = new HashSet<String>( ir.assignments.two.a.Utilities.tokenizeFile(new File(System.getProperty("user.dir").concat("/stopwords.txt"))));
    public static int maxTextLength = 0;
    public static ArrayList<Frequency> wordList = new ArrayList<Frequency>();

    public static void init(String urlSeed) throws Exception {
        String crawlStorageFolder = System.getProperty("user.dir").concat("/data/crawl/root");
        int numberOfCrawlers = NUMBER_OF_CRAWLERS;

        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);
        config.setPolitenessDelay(CRAWLER_DELAY); // setting politeness
        config.setUserAgentString(USER_AGENT);

        PageFetcher pageFetcher = new PageFetcher(config);

        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
        controller.addSeed(urlSeed);
        controller.start(ICSWebCrawler.class, numberOfCrawlers);

    }
}
