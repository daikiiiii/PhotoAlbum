package view;

import java.io.IOException;
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
import model.Database;

/**
 * AdminController is the class that repreents the capabilities of an admin. Displays the users of the app, and allows the admin
 * to create and delete users.
 * @author Khang Tuyhong
 * @author Jorge D. Gomez-Kobayashi
 * */
public class AdminController {

	@FXML         
	ListView<String> listView;
	@FXML
	Button add;
	@FXML
	Button delete;
	@FXML
	Button logout;
	
	private ObservableList<String> obsList;
	private Database u;
	private Stage stage;
	
	public void start(Stage stage) throws ClassNotFoundException, IOException {
		this.stage = stage;
		u = new Database();

		obsList = FXCollections.observableArrayList(u.getUserArr());
		listView.setItems(obsList);
		listView.getSelectionModel().select(0);
	}
	
	public void event(ActionEvent e) throws IOException, ClassNotFoundException {
		Button b = (Button)e.getSource();
		if(b == add) {
			inputAdd();
		} else if(b == delete) {
			if(obsList.size() != 0 ) {	// Check if list is empty
				clarifyDelete();
			}
		} else if(b == logout) {
			FXMLLoader loader =  new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/login.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			Scene loginScene = new Scene(root);
			
			LoginController lc = loader.getController();
			lc.start(stage);
			
			stage.setScene(loginScene);
			stage.show();
		}
	}
	
	private void clarifyDelete() throws IOException {
		String userChosen = listView.getSelectionModel().getSelectedItem();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initOwner(stage);
		alert.setTitle("Delete Clarification");
		alert.setContentText("Do you want to delete: " + userChosen + "?");
		
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get().equals(ButtonType.OK)) { 
			u.deleteUsername(userChosen);
			u.storeInstance(u);
			obsList.remove(obsList.indexOf(userChosen));
		}
	}
	
	private void inputAdd() throws IOException {
		TextInputDialog dialog = new TextInputDialog();
		dialog.initOwner(stage);
		dialog.setTitle("Add User");
		dialog.setContentText("Enter username: ");
		
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent() && !(result.get().isBlank())) { 
			int i = u.addUsername(result.get().trim());
			if(i == 1) {	// Check for duplicate
				u.storeInstance(u);
				obsList.add(result.get().trim()); 
			}
		}
	}
}
