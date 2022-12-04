package view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.User;
import model.Database;
import model.Photo;
import model.Album;

/**
 * SearchController is the class that maintains the searching logic when trying to find photos across multiple albums.
 * @author Khang Tuyhong
 * @author Jorge D. Gomez-Kobayashi
 * */
public class SearchController {
	
	@FXML
	RadioButton date;
	
	@FXML
	RadioButton tags;
	
	@FXML
	ToggleGroup category;
	
	@FXML
	ComboBox<String> from;
	
	@FXML
	ComboBox<String> to;
	
	@FXML
	ComboBox<String> nameOne;
	
	@FXML
	ComboBox<String> valueOne;
	
	@FXML
	ComboBox<String> junction;
	
	@FXML
	ComboBox<String> nameTwo;
	
	@FXML
	ComboBox<String> valueTwo;
	
	@FXML
	Button searchButton;
	
	@FXML
	Button createAlbumButton;
	
	@FXML
	Button returnButton;
	
	@FXML
	Button logoutButton;
	
	@FXML
	TilePane tilePane;
	
	Stage mainStage;
	
	private ObservableList<Photo> obsList;
	
	private ArrayList<Photo> results = new ArrayList<Photo>();
	
	private User user;
	
	private Database u;
	
	public void start(Stage mainStage, User user) throws IOException, ClassNotFoundException {
		
		this.user = user;
		
		this.mainStage = mainStage;
		
		this.u = new Database();
		
		date.setToggleGroup(category);
		
		tags.setToggleGroup(category);
		
		// dates
		if (this.user.getDateTimes() == null) {
			
			from.getItems().addAll();
			to.getItems().addAll();
			
		} else {
			
			from.getItems().addAll(this.user.getDateTimes());
			to.getItems().addAll(this.user.getDateTimes());
			
		}
		
		// names
		if (this.user.getTagNames() == null) {
			
			nameOne.getItems().addAll();
			nameTwo.getItems().addAll();
			
		} else {
			
			nameOne.getItems().addAll(this.user.getTagNames());
			nameTwo.getItems().addAll(this.user.getTagNames());
			
		}
		
		// values
		if (this.user.getTagValues() == null) {
			
			valueOne.getItems().addAll(/*this.user.getTagValues()*/);
			valueTwo.getItems().addAll(/*this.user.getTagValues()*/);
		
		} else {
			
			valueOne.getItems().addAll(this.user.getTagValues());
			valueTwo.getItems().addAll(this.user.getTagValues());
			
		}
		
		junction.getItems().addAll("and", "or");
		
		/*
		 * 
		 * GUI logic for search query goes here
		 * */
		
		obsList = FXCollections.observableArrayList(results);
		
		for (int i = 0; i < obsList.size(); i++) {
			FXMLLoader loader =  new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/albumPhoto.fxml"));
			AnchorPane pane = (AnchorPane)loader.load();
			tilePane.setHgap(10);
			tilePane.setVgap(10);
			tilePane.getChildren().add(pane);
			
			AlbumPhotoController apc = loader.getController();
			apc.start(mainStage, obsList.get(i));
		}
	}
	
	public void searchEvent(ActionEvent e) {
		
		results.clear();
		
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("ERROR!");
		
		if (date.isSelected() && tags.isSelected()) {
			// error
			String str = "Must select either 'date' or 'tags' to query your search.";
			alert.setContentText(str);
			alert.showAndWait();
			return;
		}
		if (!date.isSelected() && !tags.isSelected()) {
			// error
			String str = "Cannot select both 'date' and 'tags' -- only one or the other.";
			alert.setContentText(str);
			alert.showAndWait();
			return;
		}
		if (date.isSelected()) {
			
			if (from.getValue() == null || to.getValue() == null) {
				// error
				String str = "Please ensure that both the oldest date and earliest date are filled.";
				alert.setContentText(str);
				alert.showAndWait();
				return;
			}
			
			String olderDate = from.getValue();
			String earlierDate = to.getValue();
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			
			LocalDateTime oldDateTime = LocalDateTime.parse(olderDate, formatter);
			LocalDateTime earlyDateTime = LocalDateTime.parse(earlierDate, formatter);
			
			if (oldDateTime.isAfter(earlyDateTime)) {
				String str = "'From' date cannot be after 'to' date.";
				alert.setContentText(str);
				alert.showAndWait();
				return;
			}
			
		} else {
			
			boolean isDouble;
			boolean isConjunctive;
			
			if (junction.getValue() == null) {
				// single search
				isDouble = false;
				isConjunctive = false;
				
				if (nameOne.getValue() == null || valueOne.getValue() == null) {
					String str = "The first name and tag value must be filled";
					alert.setContentText(str);
					alert.showAndWait();
					return;
				}
				
			} else {
				isDouble = true;
				
				if (nameOne.getValue() == null || nameTwo.getValue() == null || valueOne.getValue() == null || valueTwo.getValue() == null) {
					String str = "All tag names and tag values must be filled.";
					alert.setContentText(str);
					alert.showAndWait();
					return;
				}
				
				if (junction.getValue().compareTo("and") == 0) {
					isConjunctive = true;
				} else {
					isConjunctive = false;
				}
				
			}
			
			if (isDouble) {
				
				searchQueryTagDouble(nameOne.getValue(), valueOne.getValue(), isConjunctive, nameTwo.getValue(), valueTwo.getValue());
				
			} else {
				// double for loop and find the photos??
				searchQueryTagSingle(nameOne.getValue(), nameTwo.getValue());
			}
			
		}
		
	}
	
	public void createAlbumEvent(ActionEvent e) throws IOException {
		
		TextInputDialog dialog = new TextInputDialog();
		dialog.initOwner(mainStage);
		dialog.setTitle("Create an Album");
		dialog.setContentText("Enter Album Name: ");
		
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("ERROR!");
		
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent() && !(result.get().isBlank()) && !(user.findDuplicateAlbum(result.get()))) {
			Album album = new Album(result.get(), this.user);
			for (int i = 0; i < obsList.size(); i++) {
				int photoAdd = album.addPhoto(obsList.get(i));
				if (photoAdd == -1) {
					String str = "Found a duplicate photo -- this is invalid.";
					alert.setContentText(str);
					alert.showAndWait();
					return;
				}
			}
			u.storeInstance(u);
		} else {
			String str = "Invalid input.";
			alert.setContentText(str);
			alert.showAndWait();
			return;
		}
		
		
	}
	
	public void returnEvent(ActionEvent e) throws IOException, ClassNotFoundException {
		FXMLLoader loader =  new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/user.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		Scene userScene = new Scene(root);
		
		UserController userController = loader.getController();
		userController.start(mainStage, user);
		
		mainStage.setScene(userScene);
		mainStage.show();
	}
	
	public void logoutEvent(ActionEvent e) throws IOException {
		FXMLLoader loader =  new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/login.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		Scene loginScene = new Scene(root);
		
		LoginController lc = loader.getController();
		lc.start(mainStage);
		
		mainStage.setScene(loginScene);
		mainStage.show();
		
	}
	
	public void searchQueryDate(LocalDateTime first, LocalDateTime second) {
		
		ArrayList<Album> albums = this.user.getAlbums();
		
		for (int i = 0; i < albums.size(); i++) {
			for (int j = 0; j < albums.get(i).getPhotos().size(); j++) {
				LocalDateTime currDateTime = albums.get(i).getPhotos().get(j).getDateTime();
				if (!currDateTime.isBefore(first) && !currDateTime.isAfter(second)) {
					results.add(albums.get(i).getPhotos().get(j));
				}
				
			}
		}
		
	}
	
	public void searchQueryTagSingle(String name, String value) {
		
		ArrayList<Album> albums = this.user.getAlbums();
		
		for (int i = 0; i < albums.size(); i++) {
			for (int j = 0; j < albums.get(i).getPhotos().size(); j++) {
				if (albums.get(i).getPhotos().get(j).containsTag(name, value)) {
					//obsList.add(albums.get(i).getPhotos().get(j));
					results.add(albums.get(i).getPhotos().get(j));
				}
			}
		}
	}
	
	public void searchQueryTagDouble(String name, String value, boolean conjunction, String nameTwo, String valueTwo) {
		
		ArrayList<Album> albums = this.user.getAlbums();
		
		for (int i = 0; i < albums.size(); i++) {
			for (int j = 0; j < albums.get(i).getPhotos().size(); j++) {
				Photo photo = albums.get(i).getPhotos().get(j);
				if (conjunction(conjunction, photo.containsTag(name, value), photo.containsTag(nameTwo, valueTwo))) {
					//obsList.add(albums.get(i).getPhotos().get(j));
					results.add(albums.get(i).getPhotos().get(j));
				}
			}
		}
	}
	
	public boolean conjunction(boolean conjunction, boolean one, boolean two) {
		if (conjunction) {
			return one && two;
		} else {
			return one || two;
		}
	}
	
	
	
}