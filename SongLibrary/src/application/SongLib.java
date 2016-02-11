//Benjamin Bancala and Michael Shafran
//Software Methodology Spring 2016

package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;


public class SongLib extends Application {
	 
	@Override
	public void start(Stage primaryStage) throws IOException {
		
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("songs.fxml"));
			primaryStage.setTitle("Song Library by Ben and Mike");
			AnchorPane pane = (AnchorPane) loader.load();
			Scene scene = new Scene(pane);
			
			Controller listController = loader.getController();
			listController.start(primaryStage);
			
			primaryStage.setScene(scene);
			primaryStage.show();
		}catch(Exception e){
			
		}
	}
	
	public static void main(String[] args) {
		launch(args); 
	}
}
