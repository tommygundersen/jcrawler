package io.bit.crawler.page;

import java.util.ArrayList;
import java.util.List;

public class Content {
	
	private String absoluteRef;
	private String title;
	private StringBuffer content;
	private List<Link> links;
	public Content(String absoluteRef, String title) {
		super();
		this.absoluteRef = absoluteRef;
		this.title = title;
		this.content = new StringBuffer();
		this.links = new ArrayList<>();
	}
	public void add(String content) {
		this.content.append(content);
	}
	public void add(Link link) {
		links.add(link);
	}
	public String getContent() {
		return content.toString();
	}
	public String getAbsoluteRef() {
		return absoluteRef;
	}
	public List<Link> getLinks() {
		return links;
	}
	public String getTitle() {
		return title;
	}
	@Override
	public String toString() {
		return "Content [absoluteRef=" + absoluteRef + ", title=" + title + ", content=" + content + ", links=" + links
				+ "]";
	}
	
	
	

}
