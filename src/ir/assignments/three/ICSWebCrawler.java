/**
 * Dinh Ho 73374042,
 * David Chung 87654321
 * Anthony So 83689220
 *
 * Assignment 3
 * INF 141/CS 121
 */

package ir.assignments.three;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import java.util.regex.Pattern;


public class ICSWebCrawler extends WebCrawler {

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g"
            + "|png|tiff?|mid|mp2|mp3|mp4"
            + "|wav|avi|mov|mpeg|ram|m4v|mpg|pdf"
            + "|rm|smil|wmv|swf|wma|zip|rar|gz|h|py|c|cpp|java|xml|thmx|dat||csv|pde|R|csl|bib|vcf|lif"
            + "|mso|ps|pptx|m|ppt|doc|docx|flv|data|pl|arff|tar|tgz|7z|psd|tga|dll|jar|so|a|uai|exe|wav"
            + "|raw|mat|bz2|xls|ppsx|db|bg))$");

    /**
     * determine if a url should be visisted or not, this is where we set up any trap algorithm
     *
     * @param url the page to be examined
     * @return boolean whether or not we should visit the site
     */
    @Override
    public boolean shouldVisit(WebURL url) {
        String href = url.getURL().toLowerCase();
        if (CrawlerController.didVisitURL(href) || href.contains("?")) {
            return false;
        }
        else {
            return !FILTERS.matcher(href).matches() && href.contains(".ics.uci.edu");
        }
    }


    /**
     * process the current page being crawled
     *
     * @param page the page to be process
     * @return void
     */
    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL(); // get the page's URL
        CrawlerController.addURL(url); // add the URL to our visited list
        String subDomain = url.substring(0, url.indexOf(".edu") + 4); // extract the subdomain
        CrawlerController.incrementSubDomain(subDomain); // count the subdomain
        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData(); // parse the html page
            String text = htmlParseData.getText();
            CrawlerController.checkLongestPage(text.length(), url); // check if this is our current longest page
            CrawlerController.addTextToWordList(text); // count the frequency of the words
        }
    }

}
