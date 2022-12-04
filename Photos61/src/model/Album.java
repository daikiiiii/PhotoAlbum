package model;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import java.io.*;

/**
 * Album is a serializable class that represents an album. It contains photos.
 * @author Khang Tuyhong
 * @author Jorge D. Gomez-Kobayashi
 * */
public class Album implements Serializable{

	private static final long serialVersionUID = 1L;
	private String albumName;
	private User user;
	private int numOfPhotos;
	private ArrayList<Photo> photos;

	public Album(String albumName, User user) {
		this.albumName = albumName;
		this.user = user;
		numOfPhotos = 0;
		photos = new ArrayList<Photo>();
	}
	
	public String getAlbumName() {
		return albumName;
	}
	
	public User getUser() {
		return user;
	}
	
	
	public int getNumOfPhotos() {
		return numOfPhotos;
	}
	
	public ArrayList<Photo> getPhotos() {
		return photos;
	}
	
	public Photo getThumbNail() {
		if(photos.size()!= 0) {
			return photos.get(0);
		}
		return null;
	}
	
	public void changeAlbumName(String name) {
		this.albumName = name;
	}
	
	public int addPhoto(Photo p) {
		for(Photo x: photos) {
			if(x.getImageLocation().compareTo(p.getImageLocation()) == 0) {	// Duplicate
				return -1;
			}
		}
		photos.add(p);
		numOfPhotos++;
		return 1;
	}
	
	public int removePhoto(Photo p) {
		for(Photo x: photos) {
			if(x.equals(p)) {	
				photos.remove(p);
				numOfPhotos--;
				return 1;
			}
		}
		return -1;
	}
	
	public boolean checkPhotoExistence(String filepath) {
		for(Photo x: photos) {
			File file1 = new File(filepath);
			File file2 = new File(x.getImageLocation());
			if(file1.equals(file2)) {
				return false;
			}
		}
		return true;
	}
}