package io.bit.crawler.app;

import io.bit.crawler.page.Page;
import io.bit.crawler.util.Args;

public class Crawler {
	
	public static void main(String[] args) throws Exception {
		
		Args a = new Args(args);
		
		String url = a.get("-u");
		if(url == null || url.isBlank()) {
			usage();
			System.exit(1);
		}
		
		Page page = new Page(url, 
				a.get("-o"), 
				a.get("-x"), 
				a.has("--flatten"), 
				a.getInt("-d", "1"),
				a.has("--silent"), 
				a.get("-h"),
				a.has("--skipln"));
		
		page.crawl();
		
	}
	
	private static void usage() {
		System.out.println("Usage: crawl -u <url> -x [xpath] -d [crawling depth] -o [output] [--flatten]");
		System.out.println("crawl -u https://jsoup.org -x //div[@class='col1']/p,//div[@class='col2']/p -d 2 -o /users/name/output --flatten");
		System.out.println("Arguments: ");
		System.out.println("-u  Starting point URL to crawl. Mandatory");
		System.out.println("-x  The xpath expressions to look for in this page and all sub pages. Comma separated. Optional");
		System.out.println("-d  The crawling depth of links to look for, default is 1, maximum is 3. Optional");
		System.out.println("-o  Output path to store text files, will create a directory structure from path. Optional");
		System.out.println("-h  Comma separated list of hosts to limit this crawl to, e.g. -h jsoup.org,en.wikipedia.org");
		System.out.println("--flatten   Will not create a directory structure, but store all files in same directory with a normalized file name");
		System.out.println("--silent    Does not print anything except the output (if output path is not defined). Optional");
		System.out.println("--skipln    Skip line breaks between elements in content parsed. Optional");
	}

}
