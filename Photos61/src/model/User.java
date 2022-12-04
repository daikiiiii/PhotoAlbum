package model;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import java.io.*;


/**
 * User is the class that maintains its components, such as Albums, Photos, and Tags.
 * @author Khang Tuyhong
 * @author Jorge D. Gomez-Kobayashi
 * */
public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	private String userName;
	private ArrayList<Album> albums;
	
	private ArrayList<String> tagNames;
	private ArrayList<String> tagValues;
	
	private ArrayList<String> dateTimes;
	
	/**
	 * Constructor. Takes in a string userName as its parameter. Initializes the necessary lists needed to maintain the app.
	 * */
	public User(String userName) {
		this.userName = userName;
		albums = new ArrayList<Album>();
		tagNames = new ArrayList<String>();
		tagNames.add("person");
		tagNames.add("location");
		tagValues = new	ArrayList<String>();
		dateTimes = new ArrayList<String>();
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public ArrayList<Album> getAlbums() {
		return this.albums;
	}
	
	public Album getAlbum(String name) {
		for(Album x: albums) {
			if(x.getAlbumName().compareTo(name) == 0) {
				return x;
			}
		}
		return null;
	}
	
	public void addAlbum(Album album) {
		this.albums.add(album);
	}
	
	public void removeAlbum(int index) {
		
		this.albums.remove(index);
		
	}
	
	public void addTagName(String name) {
		this.tagNames.add(name);
	}
	
	public void addTagValue(String value) {
		this.tagValues.add(value);
	}
	
	public void addDateTime(String dateTime) {
		this.dateTimes.add(dateTime);
	}
	
	public ArrayList<String> getTagNames() {
		return this.tagNames;
	}
	
	public ArrayList<String> getTagValues() {
		return this.tagValues;
	}
	
	public ArrayList<String> getDateTimes() {
		return this.dateTimes;
	}
	
	/**
	 * Finds a duplicate album that may be in the list of the User's albums.
	 * @param name - name of the album
	 * */
	public boolean findDuplicateAlbum(String name) {
		
		for (int i = 0; i < this.albums.size(); i++) {
			if (albums.get(i).getAlbumName().compareTo(name) == 0) {
				return true;
			}
		}
		return false;
		
	}
	
	public boolean findDuplicateTagName(String name) {
		
		for (int i = 0; i < this.tagNames.size(); i++) {
			if (tagNames.get(i).compareTo(name) == 0) {
				return true;
			}
		}
		
		return false;
		
	}
	
	public boolean findDuplicateTagValue(String value) {
		
		for (int i = 0; i < this.tagValues.size(); i++) {
			if (tagValues.get(i).compareTo(value) == 0) {
				return true;
			}
		}
		
		return false;
		
	}
	

	
}