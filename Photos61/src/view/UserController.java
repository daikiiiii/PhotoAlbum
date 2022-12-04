package view;

import java.io.IOException;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import model.User;
import model.Database;
import model.Album;


/**
 * UserController is the class that represents a user's home page -- displaying all their albums, and allowing them to
 * create albums, delete albums, rename albums, and look into them or search through them with criteria.
 * @author Khang Tuyhong
 * @author Jorge D. Gomez-Kobayashi
 * */
public class UserController {
	
	@FXML
	ListView<Album> listView;
	
	@FXML
	Button addButton;
	@FXML
	Button renameButton;
	@FXML
	Button deleteButton;
	@FXML
	Button openButton;
	@FXML
	Button searchButton;
	@FXML
	Button logoutButton;
	
	private User user;
	
	private ArrayList<Album> albums;
	
	private ObservableList<Album> obsList;
	
	private Stage mainStage;
	
	private Database u;
	
	public void start(Stage mainStage, User user) throws ClassNotFoundException, IOException {
		
		this.mainStage = mainStage;
		this.u = new Database();
		
		this.user = u.getUser(user.getUserName());
		
		albums = user.getAlbums();
		
		obsList = FXCollections.observableArrayList(albums);
		
		listView.setItems(obsList);
		
		// sets up the custom cells within the ListView
		listView.setCellFactory(albumListView -> new AlbumListCell());
		
		// select first item
		listView.getSelectionModel().select(0);
		
		
	}
	
	public void addAlbumEvent(ActionEvent e) throws IOException {
		

		TextInputDialog dialog = new TextInputDialog();
		dialog.initOwner(mainStage);
		dialog.setTitle("Create an Album");
		dialog.setContentText("Enter Album Name: ");
		
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent() && !(result.get().isBlank())) {
			boolean isDuplicate = user.findDuplicateAlbum(result.get().trim());
			if (!isDuplicate) {
				Album album = new Album(result.get().trim(), user);
				user.addAlbum(album);
				obsList.add(album);
				u.storeInstance(u);
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR!");
				String str = "Cannot have a duplicate album.";
				alert.setContentText(str);
				
				alert.showAndWait();
				
				return;
			}
			
		}
		
		
	}
	
	/*
	 * Need to double-check this method: need to ensure that the albums that are getting renamed are staying consistent
	 * */
	public void renameAlbumEvent(ActionEvent e) throws IOException {
		
		Album album = listView.getSelectionModel().getSelectedItem();
		int index = listView.getSelectionModel().getSelectedIndex();
		
		TextInputDialog dialog = new TextInputDialog();
		dialog.initOwner(mainStage);
		dialog.setTitle("Rename an Album");
		dialog.setContentText("Enter the new Album Name: ");
		
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent() && !(result.get().isBlank()) && !(user.findDuplicateAlbum(result.get()))) {
			Album al = user.getAlbum(album.getAlbumName());
			al.changeAlbumName(result.get().trim());
			u.storeInstance(u);
			obsList.remove(index);
			obsList.add(index, al);
		}
		
		
	}
	
	public void deleteAlbumEvent(ActionEvent e) throws IOException {
		
		int index = listView.getSelectionModel().getSelectedIndex();
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initOwner(mainStage);
		alert.setTitle("Delete Clarification");
		alert.setContentText("Do you want to delete: " + obsList.get(index).getAlbumName() + "?");
		
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get().equals(ButtonType.OK)) {
			user.removeAlbum(index);
			u.storeInstance(u);
			obsList.remove(obsList.get(index));
		}
		
	}
	
	public void openAlbumEvent(ActionEvent e) throws IOException, ClassNotFoundException {
		
		Album album = listView.getSelectionModel().getSelectedItem();
		
		FXMLLoader loader =  new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/album.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		Scene openScene = new Scene(root);
		
		AlbumController albumController = loader.getController();
		albumController.start(mainStage, album);
		
		mainStage.setScene(openScene);
		mainStage.show();
	}
	
	public void searchEvent(ActionEvent e) throws IOException, ClassNotFoundException {
		FXMLLoader loader =  new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/search.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		Scene searchScene = new Scene(root);
		
		SearchController searchController = loader.getController();
		searchController.start(mainStage, user);
		
		mainStage.setScene(searchScene);
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
	
	
}