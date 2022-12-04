package model;

import java.io.*;
import java.util.ArrayList;

/**
 * Database is a class that maintains and serializes all data in the app. This allows the program to retrieve and store data with having to
 * store physical memory on the project space.
 * @author Khang Tuyhong
 * @author Jorge D. Gomez-Kobayashi
 * */
public class Database implements Serializable {
	static final long serialVersionUID = 1L;
	
	private ArrayList<User> users;
	private ArrayList<String> usernames;
	private ArrayList<String> admin;
	private ArrayList<Photo> photos;
	private ArrayList<Tag> tags;
	
	public static final String storeDir = "dat";
	public static final String storeFile = "database.dat";
	
	public Database() throws ClassNotFoundException, IOException {
		File f = new File(storeDir + File.separator + storeFile);
		
		// First time 
		if( f.length() == 0) {
			users = new ArrayList<User>();
			usernames = new ArrayList<String>();
			admin = new ArrayList<String>();
			photos = new ArrayList<Photo>();
			tags = new ArrayList<Tag>();
			
			User u = new User("stock");
			Album a = new Album("stock", u);
			Photo p1 = new Photo("data/img1.jpg", "caption1");
			Photo p2 = new Photo("data/img2.jpg", "caption2");
			Photo p3 = new Photo("data/img3.jpg", "caption3");
			Photo p4 = new Photo("data/img4.jpg", "caption4");
			Photo p5 = new Photo("data/img5.jpg", "caption5");
			Photo p6 = new Photo("data/img6.jpg", "caption6");
			Photo p7 = new Photo("data/img7.jpg", "caption7");
			
			
			/* Compiles Tag Values for Stock*/
			u.addTagValue("aurora");
			u.addTagValue("blue");
			u.addTagValue("wallpaper");
			u.addTagValue("woods");
			u.addTagValue("water");
			
			p1.addTag(new Tag("name", "aurora"));
			p1.addTag(new Tag("location", "water"));
			p2.addTag(new Tag("location", "woods"));
			p3.addTag(new Tag("name", "water"));
			p4.addTag(new Tag("location", "blue"));
			p5.addTag(new Tag("location", "aurora"));
			p6.addTag(new Tag("name", "water"));
			p7.addTag(new Tag("name", "water"));
			
			a.addPhoto(p1);
			a.addPhoto(p2);
			a.addPhoto(p3);
			a.addPhoto(p4);
			a.addPhoto(p5);
			a.addPhoto(p6);
			a.addPhoto(p7);
			u.addAlbum(a);
			
			photos.add(p1);
			photos.add(p2);
			photos.add(p3);
			photos.add(p4);
			photos.add(p5);
			photos.add(p6);
			photos.add(p7);
			
			users.add(u);
			usernames.add("stock");
			admin.add("admin");
		} else {
			users = retrieveInstance().users;
			usernames = retrieveInstance().getUserArr();
			admin = retrieveInstance().admin;
			photos = retrieveInstance().photos;
			tags = retrieveInstance().tags;
			if(photos == null) {
				System.out.println("heo");
			}
		}
	}
	
	public User getUser(String name) {
		if(isUser(name)) {
			return users.get(usernames.indexOf(name));
		}
		return null;
	}
	
	public int addUsername(String n) {
		// Check duplicate in users
		for(User x: users) {
			if(n.compareTo(x.getUserName()) == 0) {
				return -1;
			}
		}
		// Check duplicate in admin
		for(String x: admin) {
			if(n.compareTo(x) == 0) {
				return -1;
			}
		}

		users.add(new User(n));
		usernames.add(n);
		return 1;
	}
	
	public void deleteUsername(String n) {
		for(int i=0; i<users.size(); i++) {
			if(n.compareTo(users.get(i).getUserName()) == 0) {
				users.remove(i);
				usernames.remove(i);
				return;
			}
		}
	}
	
	public boolean isUser(String n) {
		for(User x: users) {
			if(n.compareTo(x.getUserName()) == 0) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isAdmin(String n) {
		for(String x: admin) {
			if(n.compareTo(x) == 0) {
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<String> getUserArr(){
		return usernames;
	}
	
	public ArrayList<User> getUserObjArr(){
		return users;
	}
	
	public boolean checkPhotoExistence(String filepath) {
		if(photos != null) {
			for(Photo x: photos) {
				File file1 = new File(filepath);
				File file2 = new File(x.getImageLocation());
				if(file1.equals(file2)) {
					return false;
				}
			}
		}
		return true;
	}
	
	public void addPhoto(Photo p) {
		
		photos.add(p);
	}
	
	public void printDatabase() {
		for (User x: users) {
			System.out.println(x.getUserName());
			for(Album y: x.getAlbums()) {
				System.out.print("\t");
				System.out.println(y.getAlbumName());
				for(Photo z: y.getPhotos()) {
					System.out.print("\t\t");
					System.out.println(z.getImageLocation());
				}
			}
		}
	}
	
	public static void storeInstance(Database u) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream(storeDir + File.separator + storeFile));
		oos.writeObject(u);
		oos.close();
	}
	
	public static Database retrieveInstance() throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream(storeDir + File.separator + storeFile));
		Database u = (Database)ois.readObject();
		ois.close();
		return u;
	}

}
