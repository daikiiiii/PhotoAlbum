package view;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import java.io.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Database;
import model.User;

/**
 * LoginController represents the login screen. The user can input a username that will allow them to view their albums. If they input
 * "admin" they are able to go into the Admin-Subsystem.
 * @author Khang Tuyhong
 * @author Jorge D. Gomez-Kobayashi
 * */
public class LoginController {
	
	@FXML
	TextField userName;
	@FXML
	Button loginButton;
	
	Stage stage;
	
	public void start(Stage mainStage) {
		stage = mainStage;
	}
	
	public void loginEvent(ActionEvent e) throws IOException, ClassNotFoundException {
		
		Button b = (Button)e.getSource();
		String userInput = userName.getText();
		Database u = new Database();
		
		if(b == loginButton) {
			if(u.isAdmin(userInput)) {
				FXMLLoader loader =  new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/admin.fxml"));
				AnchorPane root = (AnchorPane)loader.load();
				Scene adminScene = new Scene(root);
				
				AdminController ac = loader.getController();
				ac.start(stage);
				
				stage.setScene(adminScene);
				stage.show();
				
			} else if (u.isUser(userInput)) {
				
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/user.fxml"));
				AnchorPane root = (AnchorPane)loader.load();
				Scene userScene = new Scene(root);
				
				UserController userController = loader.getController();
				
				userController.start(stage, u.getUser(userInput));
				
				stage.setScene(userScene);
				stage.show();
			}
		}
		
	}
	
}