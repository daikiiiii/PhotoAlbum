package model;

import java.io.*;

/**
 * Tag is the class that consists of a tag-name and tag-value pair. Tags are components of photos.
 * @author Khang Tuyhong
 * @author Jorge D. Gomez-Kobayashi
 * */
public class Tag implements Serializable {

	private static final long serialVersionUID = 1L;
	private String tagName;
	private String tagValue;
	
	public Tag(String tagName, String tagValue) {
		this.tagName = tagName;
		this.tagValue = tagValue;
	}
	
	public String getName() {
		return this.tagName;
	}
	
	public String getValue() {
		return this.tagValue;
	}
	
	public String toString() {
		return this.tagName + ": " + this.tagValue;
	}
	
}