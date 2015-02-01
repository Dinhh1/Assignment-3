package ir.assignments.three;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;


/**
 * Created by dinhho on 1/31/15.
 */
public class CrawlerController {
    public CrawlerController() {}


    private static final String USER_AGENT = "UCI Inf141-CS121 crawler 73374042 82757831 83689220";
    private static final int CRAWLER_DELAY = 300;
    private static final int NUMBER_OF_CRAWLERS = 1;

    public static void init() throws Exception {
        String crawlStorageFolder = "/data/crawl/root";
        int numberOfCrawlers = NUMBER_OF_CRAWLERS;

        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);
        config.setPolitenessDelay(CRAWLER_DELAY); // setting politeness
        config.setUserAgentString(USER_AGENT);

        PageFetcher pageFetcher = new PageFetcher(config);

        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
        controller.addSeed("http://www.ics.uci.edu");
        controller.start(ICSWebCrawler.class, numberOfCrawlers);
    }
}
