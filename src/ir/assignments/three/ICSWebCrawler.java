package ir.assignments.three;

import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

import java.util.ArrayList;
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
            + "|rm|smil|wmv|swf|wma|zip|rar|gz|h|py|c|cpp|java|xml|thmx|dat|txt||csv|pde|R|csl|bib))$");
//    private final static Pattern FILTERS = Pattern.compile(".*(\\.(php|htm|html|asp|jsp))$");

    /**
     * You should implement this function to specify whether
     * the given url should be crawled or not (based on your
     * crawling logic).
     */
    @Override
    public boolean shouldVisit(WebURL url) {
        String href = url.getURL().toLowerCase();
        // here to check if we've visited a page already, we won't visit that page
        if (CrawlerController.urls.contains(href)) {
            System.out.println("We've been to the URL: " + href); // printing that we've been here and wont visit\
            return false;
        }         // handle sub domain matching logic
        else
            return !FILTERS.matcher(href).matches() && href.startsWith("http://www.ics.uci.edu/");
    }
    

    /**
     * This function is called when a page is fetched and ready
     * to be processed by your program.
     */

    /*

    What is the longest page in terms of number of words? (Don't count HTML markup as words.)
    What are the 500 most common words in this domain? (Ignore English stop words, which can be found, for example, at http://www.ranks.nl/stopwords.)
    Submit the list of common words ordered by frequency (and alphabetically for words with the same frequency) in a file called CommonWords.txt.

    */

    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();

        //Add url to visited list
        CrawlerController.urls.add(url);
        //print the added url
        System.out.println("URL: " + url);

        String subdomain = url;
        int lastIndex = url.indexOf(".edu");
        subdomain = url.substring(0,lastIndex+4);
        if (CrawlerController.domainMap.containsKey(subdomain)) {
            CrawlerController.domainMap.get(subdomain).add(url);
        } else {
            CrawlerController.domainMap.put(subdomain, new HashSet<String>());
            CrawlerController.domainMap.get(subdomain).add(url);
        }


        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String text = htmlParseData.getText();
            String html = htmlParseData.getHtml();
            List<WebURL> links = htmlParseData.getOutgoingUrls();

            //Obtain longest text length
            if (text.length() > CrawlerController.maxTextLength)
                CrawlerController.maxTextLength = text.length();

            //Obtain 500 most common words


            System.out.println("Text length: " + text.length());
            System.out.println("Html length: " + html.length());
            System.out.println("Number of outgoing links: " + links.size());
        }
    }

}
