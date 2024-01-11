package io.bit.crawler.page;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Page {

	private static final String allowedChars = "abcdefghijklmnopqrstuvwxyz1234567890-";
	private String absoluteRef;
	private File workingDir;
	private boolean shouldStore;
	private URL urlRef;
	private String[] hostLimits;
	private String[] xpaths;
	private boolean flatten;
	private int depth;
	private boolean silent;
	private Set<String> urlsCrawled = new HashSet<>();
	private int size = 0;
	private boolean skipLineBreaks;
	
	public Page(String absoluteRef, String outputPath, String xpathSelectors, boolean flatten, int depth, boolean silent, String hostLimits, boolean skipLineBreaks) throws MalformedURLException {
		super();
		this.absoluteRef = absoluteRef;		
		this.workingDir = makeSureDirExist(outputPath);
		if(!this.absoluteRef.startsWith("http")) {
			this.absoluteRef = "https://" + this.absoluteRef;
		}
		this.urlRef = new URL(this.absoluteRef);
		this.xpaths = xpathSelectors != null ? xpathSelectors.split(",") : null;
		this.flatten = flatten;
		this.depth = depth;
		this.shouldStore = this.workingDir != null;
		this.silent = silent;
		this.hostLimits = hostLimits != null ? hostLimits.split(",") : null;
		this.skipLineBreaks = skipLineBreaks;
	}

	public void crawl() throws IOException {			
		crawl(absoluteRef, this.depth-1, "");		
		out("downloaded " + (size / 1024) + "K bytes of content");
	}
	
	private void crawl(String url, int depth, String indent) throws IOException {
		if(urlsCrawled.contains(url)) {
			return;
		}
		if(!isAcceptedHost(url)) {
			out(indent + "skipping " + url + " ...");
			return;
		}
		out(indent + "crawling " + url + " ...");
		
		Document doc = Jsoup.connect(url).get();		
		Content pageContent = new Content(url, doc.title());
		Elements elements = getAllElements(doc);
		for(Element e : elements) {
			pageContent.add(e.text());
			if(!this.skipLineBreaks) {
				pageContent.add("\n\n");
			}
			Elements links = e.select("a[href]");
			if(links.size() > 0) {
				for(Element link : links) {
					pageContent.add(new Link(link.text(),link.attr("abs:href")));
				}
			}
		}
		if(depth > 0) {
			for(Link link : pageContent.getLinks()) {
				crawl(link.getAbsoluteRef(), depth-1, indent + "  ");
			}
		}
		if(shouldStore) {
			store(pageContent);
		} else {
			print(pageContent);
		}
		urlsCrawled.add(url);
		size = size + pageContent.getContent().length();
	}
	
	private Elements getAllElements(Document doc) {
		if(xpaths == null || xpaths.length == 0) {
			return doc.getAllElements();
		} else {
			Elements elements = doc.selectXpath(xpaths[0]);
			for(int i = 1; i < xpaths.length; i++) {
				Elements nextElements = doc.selectXpath(xpaths[i]);
				for(Element e : nextElements) {
					System.out.println(e);
					elements.add(e);
				}
			}
			return elements;
		}
	}
	
	private File makeSureDirExist(String file) {
		if(file == null || file.isBlank()) {
			return null;
		}
		File dir = new File(file);
		if(!dir.exists()) {
			dir.mkdirs();
		} else if(!dir.isDirectory()) {
			throw new IllegalArgumentException(file + " is not a directory");
		}
		return dir;
	}
	
	private void store(Content content) throws IOException {
		File file = toFile(content);		
		FileOutputStream out = new FileOutputStream(file);
		out.write(content.getContent().getBytes());
		out.close();
	}
	
	private void print(Content content) {		
		System.out.print(content.getContent());
	}
	
	private File toFile(Content content) throws MalformedURLException {
		URL url = new URL(content.getAbsoluteRef());
		String host = url.getHost();		
		String path = toFilePath(url.getPath().split("/"));
		String filename = toFilename(content.getTitle());
		if(flatten) {
			return new File(this.workingDir + "/" + host.replace("\\.", "-") + path.replace("/", "-") + filename);
		} else {
			File dir = new File(this.workingDir + "/" + host + path);
			dir.mkdirs();
			return new File(this.workingDir + "/" + host + path + "/" + filename);
		}
	}
	
	private String toFilePath(String[] paths) {
		StringBuffer buf = new StringBuffer();
		for(int i = 0; i < paths.length-1; i++) {
			buf.append("/" + paths[i]);
		}
		return buf.toString();
	}
	
	private String toFilename(String title) {		
		title = title.toLowerCase().replace(' ', '-');
		StringBuffer buf = new StringBuffer();
		for(char c : title.toCharArray()) {
			if(isAllowed(c)) {
				buf.append(c);
			}
		}
		buf.append(".txt");
		return buf.toString();
	}
	
	private boolean isAllowed(char test) {
		for(char c : allowedChars.toCharArray()) {
			if(c == test) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isAcceptedHost(String url) throws MalformedURLException {
		if(hostLimits == null) {
			return true;
		}
		URL u = new URL(url);
		String urlHost = u.getHost();
		for(String host : hostLimits) {
			if(host.equalsIgnoreCase(urlHost)) {
				return true;
			}
		}
		return false;
	}
	
	private void out(String msg) {
		if(!silent) {
			System.out.println(msg);
		}
	}
	
	

}
