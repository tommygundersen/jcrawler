package io.bit.crawler.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Args {
	
	private String[] args;
	private HashMap<String,String> map = new HashMap<>();
	private List<String> list = new ArrayList<>();
	
	public Args(String[] args) {
		this.args = args;
		this.parse();
	}
	private void parse() {
		for(int i = 0; i < args.length; i++) {
			String a = args[i];
			if(a.startsWith("-")) {
				if(i+1 >= args.length) {
					map.put(a, "");
				} else {
					map.put(a, args[i+1]);
				}
			} else {
				list.add(a);
			}
		}
	}

	public int size() {
		return list.size();
	}

	public String[] getStringArray(String arg, String defaultValue) {
		return get(arg, defaultValue).split(",");
	}
	
	public int[] getIntArray(String arg, String defaultValue) {
		String[] s = get(arg, defaultValue).split(",");
		int[] r = new int[s.length];
		for(int i = 0; i < r.length; i++) {
			r[i] = Integer.parseInt(s[i].replaceAll(" ", ""));
		}
		return r;
	}
	
	public int getInt(String arg, String defaultValue) {
		return Integer.parseInt(get(arg, defaultValue));
	}

	public String get(String arg, String defaultValue) {
		String value = map.get(arg);
		if(value == null) {
			return defaultValue;			
		}
		return value;
	}

	public String get(String arg) {
		return map.get(arg);
	}

	public String get(int i) {
		return list.get(i);
	}
	
	public boolean has(String arg) {
		return map.containsKey(arg);
	}

}
