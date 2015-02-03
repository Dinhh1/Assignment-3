package ir.assignments.three;

import com.sun.tools.javac.util.Pair;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

import java.util.HashMap;
import java.util.HashSet;

import java.io.File;

/**
 * Created by dinhho on 1/31/15.
 */
public class CrawlerController {
    public CrawlerController() {}


    private static final String USER_AGENT = "UCI Inf141-CS121 crawler TEST"; //73374042 87654321 83689220";

    private static final int CRAWLER_DELAY = 300;
    private static final int NUMBER_OF_CRAWLERS = 100;


    //Key: Doman, Value: Sub-Domain Set
    public static HashMap<String, Frequency> domainMap = new HashMap<String, Frequency>();
    public static HashSet<String> urls = new HashSet<String>();
    public static HashSet<String> stopwords = new HashSet<String>( Utils.tokenizeFile(new File(System.getProperty("user.dir").concat("/stopwords.txt"))));
//    public static int maxTextLength = 0;
    public static Pair<String, Integer> longestPage = new Pair<String, Integer>("", 0);

    public static HashMap<String, Frequency> wordList = new HashMap<String, Frequency>();

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
