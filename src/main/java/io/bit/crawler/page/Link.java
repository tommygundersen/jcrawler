package io.bit.crawler.page;

public class Link {
	
	private String title;
	private String absoluteRef;
	
	public Link(String absoluteRef) {
		this("unknown", absoluteRef);
	}

	public Link(String title, String absoluteRef) {
		super();
		this.title = title;
		this.absoluteRef = absoluteRef;
	}

	public String getTitle() {
		return title;
	}

	public String getAbsoluteRef() {
		return absoluteRef;
	}

	@Override
	public String toString() {
		return "Link [title=" + title + ", absoluteRef=" + absoluteRef + "]";
	}
	
	
	
	

}
