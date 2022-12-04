package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Photo;

/**
 * AlbumPhotoController is the class that passes a photo representation into the GUI of the AlbumController.
 * @author Khang Tuyhong
 * @author Jorge D. Gomez-Kobayashi
 * */
public class AlbumPhotoController {
	
	@FXML 
	Label caption;
	@FXML
	ImageView imageView;
	
	Photo p;
	Stage stage;
	
	public void start(Stage stage, Photo p) throws FileNotFoundException {
		this.p = p;
		this.stage = stage;
		caption.setText(p.getCaption());
		FileInputStream input = new FileInputStream(p.getImageLocation());
		Image image = new Image(input);
		imageView.setImage(image);
	}
	
	public void handle(MouseEvent e) throws ClassNotFoundException, IOException {
		FXMLLoader loader =  new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/photo.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		Scene photoScene = new Scene(root);
		
		PhotoController photoController = loader.getController();
		photoController.start(stage, p);
		
		stage.setScene(photoScene);
		stage.show();
	}

}
