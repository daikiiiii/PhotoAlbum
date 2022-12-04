package app;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.LoginController;

/**
 * Photos is the main class that is the root of the app.
 * @author Khang Tuyhong
 * @author Jorge D. Gomez-Kobayashi
 * */
public class Photos extends Application {
	
	Stage mainStage;
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		
		mainStage = primaryStage;
		
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/login.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			Scene loginScene = new Scene(root);
			
			LoginController loginController = loader.getController();
			loginController.start(mainStage);
			
			mainStage.setScene(loginScene);
			mainStage.setTitle("Login");
			mainStage.setResizable(false);
			mainStage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
} 