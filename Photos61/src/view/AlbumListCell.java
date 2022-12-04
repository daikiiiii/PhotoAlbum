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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.User;
import model.Database;
import model.Album;

/**
 * AlbumListCell is the class that represents a special type of cell for the listview in UserController. This allows for very
 * efficient and easy ability to select an album.
 * @author Khang Tuyhong
 * @author Jorge D. Gomez-Kobayashi
 * */
public class AlbumListCell extends ListCell<Album> {
	
	@FXML
	ImageView thumbnail = new ImageView();
	
	@FXML
	Label albumName = new Label();
	
	@FXML
	Label albumNumOfPhotos = new Label();
	
	@FXML
	Label albumDateRange = new Label();
	
	@FXML
	GridPane gridPane;
	
	private FXMLLoader cellLoader;
	
	@Override
	protected void updateItem(Album album, boolean empty) {
		
		super.updateItem(album, empty);
		
		if (empty || album == null) {
			
			thumbnail.setImage(null);
			albumName.setText("");
			albumNumOfPhotos.setText("");
			albumDateRange.setText("");
		
		} else {
			
			if (cellLoader == null) {
				cellLoader = new FXMLLoader();
				cellLoader.setLocation(getClass().getResource("/view/albumlistcell.fxml"));
				cellLoader.setController(this);
				
				try {
					cellLoader.load();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if (album.getNumOfPhotos() != 0) {
				Image image = new Image(new File(album.getThumbNail().getImageLocation()).toURI().toString());
				thumbnail.setImage(image);
			} else {
				thumbnail.setImage(null);
			}
			
			albumName.setText(album.getAlbumName());
			albumNumOfPhotos.setText(String.valueOf(album.getNumOfPhotos()));
			albumDateRange.setText("##/##/#### - ##/##/####");
			
			setGraphic(gridPane);
			
		}
		
	}
	
}