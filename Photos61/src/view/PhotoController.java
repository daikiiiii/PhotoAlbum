package view;

import java.io.File;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Album;
import model.Database;
import model.Photo;
import model.User;
import model.Tag;

/**
 * PhotoController is the class that displays a specific photo when selected from the album list.
 * @author Khang Tuyhong
 * @author Jorge D. Gomez-Kobayashi
 * */
public class PhotoController {
	
	@FXML
	Label dateTimeCapture;
	
	@FXML
	Label caption;
	
	@FXML
	Button addTag;
	
	@FXML
	Button deleteTag;
	
	@FXML
	Button recaption;
	
	@FXML
	Button returnButton;
	
	@FXML
	Button logoutButton;
	
	@FXML
	ImageView thumbnail;
	
	@FXML
	ListView<String> listView;
	
	private ObservableList<String> obsList;
	
	private Stage mainStage;
	
	private ArrayList<Tag> tags;
	
	private Database u;
	
	private Photo photo;
	
	public void start(Stage mainStage, Photo photo) throws ClassNotFoundException, IOException {
		
		this.photo = photo;
		
		u = new Database();
		
		Image image = new Image(new File(photo.getImageLocation()).toURI().toString());
		
		thumbnail.setImage(image);
		caption.setText(this.photo.getCaption());
		dateTimeCapture.setText(this.photo.dateTimeToString());
		
		tags = photo.getTags();
		
		obsList = FXCollections.observableArrayList();
		
		for (int i = 0; i < tags.size(); i++) {
			obsList.add(tags.get(i).toString());
		}
		
		listView.setItems(obsList);
		
		listView.getSelectionModel().select(0);
		
	}
	
	public void addTagEvent(ActionEvent e) {
		
		
		
	}
	
	public void deleteTagEvent(ActionEvent e) throws IOException {
		
		int index = listView.getSelectionModel().getSelectedIndex();
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initOwner(mainStage);
		alert.setTitle("Delete Clarification");
		alert.setContentText("Do you want to delete: " + obsList.get(index) + "?");
		
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get().equals(ButtonType.OK)) {
			tags.remove(index);
			u.storeInstance(u);
			obsList.remove(obsList.get(index));
		}
		
	}
	
	public void recaptionEvent(ActionEvent e) throws IOException {
		
		TextInputDialog dialog = new TextInputDialog();
		dialog.initOwner(mainStage);
		dialog.setTitle("Rename an Album");
		dialog.setContentText("Enter the new photo caption: ");
		
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent() && !(result.get().isBlank()) && result.get().compareTo(this.photo.getCaption()) != 0) {
			this.photo.setCaption(result.get());
			u.storeInstance(u);
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR!");
			String str = "Invalid input.";
			alert.setContentText(str);
			
			alert.showAndWait();
			
			return;
		}
		
	}
	
	public void returnEvent(ActionEvent e) throws IOException, ClassNotFoundException {
		/*
		FXMLLoader loader =  new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/user.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		Scene userScene = new Scene(root);
		
		UserController userController = loader.getController();
		userController.start(mainStage, user);
		
		mainStage.setScene(userScene);
		mainStage.show();
		*/
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