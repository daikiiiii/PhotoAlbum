package view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Album;
import model.Database;
import model.Photo;
import model.User;

/**
 * AlbumController is the class that represents the interface for an Album. Displays all photos of an album and alows the user to select
 * a photo, add a photo, delete a photo, or view a photo.
 * @author Khang Tuyhong
 * @author Jorge D. Gomez-Kobayashi
 * */
public class AlbumController {
	
	@FXML
	Button add;
	@FXML
	Button delete;
	@FXML
	Button back;
	@FXML
	TilePane tilepane;
	@FXML
	Label name;
	
	Stage stage;
	Album album;
	Database d;
	
	public void start(Stage stage, Album alb) throws IOException, ClassNotFoundException {
		d = new Database();
		this.stage = stage;
		this.album = d.getUser(alb.getUser().getUserName()).getAlbum(alb.getAlbumName());
		
		ArrayList<Photo> photoArr = album.getPhotos();
		tilepane.setMouseTransparent(false);
		tilepane.setHgap(10);
		tilepane.setVgap(10);
		
		name.setText("Album "+ album.getAlbumName());
		for(Photo x: photoArr) {
			FXMLLoader loader =  new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/albumPhoto.fxml"));
			AnchorPane pane = (AnchorPane)loader.load();
			
			tilepane.getChildren().add(pane);
			
			AlbumPhotoController apc = loader.getController();
			apc.start(stage, x);
			
		}
	}
	
	public void event(ActionEvent e) throws IOException, ClassNotFoundException {
		Button b = (Button)e.getSource();
		if(b == add) {
			FileChooser fc = new FileChooser();
			File file = fc.showOpenDialog(stage);
			if(file != null && album.checkPhotoExistence(file.getAbsolutePath())) {
				Photo p = new Photo(file.getAbsolutePath(), file.getName());
				album.addPhoto(p);
				
				FXMLLoader loader =  new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/albumPhoto.fxml"));
				AnchorPane pane = (AnchorPane)loader.load();
				
				tilepane.getChildren().add(pane);
				AlbumPhotoController apc = loader.getController();
				apc.start(stage, p);
				
				if(d.checkPhotoExistence(file.getAbsolutePath())) {
					d.addPhoto(p);
				}
				d.storeInstance(d);
			}
		} else if(b == delete) {
			
		} else if(b == back) {
			FXMLLoader loader =  new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/user.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			Scene userScene = new Scene(root);
			
			UserController userController = loader.getController();
			userController.start(stage, album.getUser());
			
			stage.setScene(userScene);
			stage.show();
		}
	}
}
