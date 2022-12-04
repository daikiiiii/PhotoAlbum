package model;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Photo is the class that represents a Photo within an Album. Has components such as its caption, image, tags, and date-time.
 * @author Khang Tuyhong
 * @author Jorge D. Gomez-Kobayashi
 * */
public class Photo implements Serializable{

	private static final long serialVersionUID = 1L;

	// create a completely new Image class?
	private String imageLocation;
	
	private String caption;
	
	private ArrayList<Tag> tags;
	
	private LocalDateTime lastModified;
	
	public Photo(String imageLocation, String caption) {
		this.imageLocation = imageLocation;
		this.caption = caption;
		lastModified = LocalDateTime.now();
		tags = new ArrayList<Tag>();
	}
	
	public String getImageLocation() {
		return this.imageLocation;
	}
	
	public String getCaption() {
		return this.caption;
	}
	
	public LocalDateTime getDateTime() {
		return this.lastModified;
	}
	
	public String dateTimeToString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return this.lastModified.format(formatter);
	}
	
	public ArrayList<Tag> getTags() {
		return this.tags;
	}
	
	public void addTag(Tag tag) {
		this.tags.add(tag);
	}
	
	public boolean containsTag(String name, String value) {
		
		for (int i = 0; i < tags.size(); i++) {
			if (tags.get(i).getName().compareTo(name) == 0 && tags.get(i).getValue().compareTo(value) == 0) {
				return true;
			}
		}
		return false;
		
	}
	
	public void setCaption(String newCaption) {
		this.caption = newCaption;
	}
	
	
}