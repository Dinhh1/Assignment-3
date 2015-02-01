package ir.assignments.three;

import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by dch on 1/31/15.
 */
public class ICSWebCrawler extends WebCrawler {
//    private static HashSet<String> visited = new HashSet<String>();
    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g"
            + "|png|tiff?|mid|mp2|mp3|mp4"
            + "|wav|avi|mov|mpeg|ram|m4v|pdf"
            + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

    /**
     * You should implement this function to specify whether
     * the given url should be crawled or not (based on your
     * crawling logic).
     */
    @Override
    public boolean shouldVisit(WebURL url) {
        boolean success = false;
        String href = url.getURL().toLowerCase();
        // here to check if we've visited a page already, we won't visit that page
        if (CrawlerController.urls.contains(href)) {
            System.out.println("We've been to the URL: " + href); // printing that we've been here and wont visit
            success = false;
        }         // handle sub domain matching logic
        else if (!FILTERS.matcher(href).matches() && href.contains("http://www.ics.uci.edu/")) {
           CrawlerController.urls.add(href);
           success = true;
        }
        return success;
    }

    /**
     * This function is called when a page is fetched and ready
     * to be processed by your program.
     */
    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        System.out.println("URL: " + url);

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String text = htmlParseData.getText();
            String html = htmlParseData.getHtml();
            List<WebURL> links = htmlParseData.getOutgoingUrls();

            System.out.println("Text length: " + text.length());
            System.out.println("Html length: " + html.length());
            System.out.println("Number of outgoing links: " + links.size());
        }
    }

}
