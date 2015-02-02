package ir.assignments.three;

import com.sun.tools.javac.util.Pair;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import ir.assignments.two.a.Frequency;
import ir.assignments.two.a.Utilities;
import ir.assignments.two.b.WordFrequencyCounter;

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
            + "|wav|avi|mov|mpeg|ram|m4v|mpg|pdf"
            + "|rm|smil|wmv|swf|wma|zip|rar|gz|h|py|c|cpp|java|xml|thmx|dat||csv|pde|R|csl|bib|vcf|lif"
            + "|mso|ps|pptx|m|ppt|doc|docx|flv|data|pl|arff|tar|tgz|7z))$");
//    private final static Pattern FILTERS = Pattern.compile(".*(\\.(php|htm|html|asp|jsp))$");

    /**
     * You should implement this function to specify whether
     * the given url should be crawled or not (based on your
     * crawling logic).
     */
    @Override
    public boolean shouldVisit(WebURL url) {
        String href = url.getURL().toLowerCase();
        // crawler trap logic here
        //TODO:: NEED TO MAKE SURE WE AVOID ALL TRAPS HERE
        // here to check if we've visited a page already, we won't visit that page
        if (CrawlerController.urls.contains(href)) {
            return false;
        }
        else if (href.contains("?")) {// the ? indiciates query string from a page that can create an infinite number of websites, we will ignore any ?
            return false;
        }
        else
            return !FILTERS.matcher(href).matches() && href.contains(".ics.uci.edu");
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
//        System.out.println("URL: " + url);

        String subdomain = url;
        int lastIndex = url.indexOf(".edu");
        subdomain = url.substring(0,lastIndex+4);

        if (CrawlerController.domainMap.containsKey(subdomain)) {
            CrawlerController.domainMap.get(subdomain).incrementFrequency();
        } else {
            CrawlerController.domainMap.put(subdomain, new Frequency(subdomain, 1));
            System.out.println("added new domain: " + subdomain);
        }


        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String text = htmlParseData.getText();
//            String html = htmlParseData.getHtml();
//            List<WebURL> links = htmlParseData.getOutgoingUrls();

            //Obtain longest text length
            if (text.length() > CrawlerController.longestPage.snd)
                CrawlerController.longestPage = new Pair<String, Integer>(url, text.length());
            CrawlerController.wordList = Utils.computeWordFrequency(Utilities.tokenizeString(text), CrawlerController.wordList);

            //Obtain 500 most common words
//            CrawlerController.wordList.addAll(WordFrequencyCounter.computeWordFrequencies(Utilities.tokenizeString(text)));

//            System.out.println("Text length: " + text.length());
//            System.out.println("Html length: " + html.length());
//            System.out.println("Number of outgoing links: " + links.size());
        }
    }

}
