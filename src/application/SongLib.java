package application;
	
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class SongLib extends Application {
	@Override
	public void start(Stage stage) {
		Scene scene = new Scene(new Group());
		stage.setTitle("Song Library");
		stage.setWidth(500);
		stage.setHeight(700);
			
		final Label label = new Label ("Song Library");
		label.setFont(new Font ("Ariel",20));
		
		table.setEditable(true);
		
		TableColumn songNameCol = new TableColumn("Song");
			songNameCol.setMinWidth(100);
			songNameCol.setCellValueFactory(new PropertyValueFactory<SongEntry, String>("song"));
		TableColumn artistCol = new TableColumn("Artist");
			artistCol.setMinWidth(100);
			artistCol.setCellValueFactory(new PropertyValueFactory<SongEntry, String>("artist"));
		TableColumn albumCol = new TableColumn("Album");
			albumCol.setMinWidth(100);
			albumCol.setCellValueFactory(new PropertyValueFactory<SongEntry, String>("album"));
		TableColumn yearCol = new TableColumn ("Year");
			yearCol.setMinWidth(100);
			yearCol.setCellValueFactory(new PropertyValueFactory<SongEntry, String>("year"));
		
		table.setItems(data);
		table.getColumns().addAll(songNameCol,artistCol,albumCol,yearCol);
		
		final TextField addNewSong = new TextField();
        addNewSong.setPromptText("Song Name");
        addNewSong.setMaxWidth(songNameCol.getPrefWidth());
        final TextField addArtist = new TextField();
        addArtist.setPromptText("Artist Name");
        addArtist.setMaxWidth(artistCol.getPrefWidth());
        final TextField addAlbum = new TextField();        
        addAlbum.setPromptText("Album Name");
        addAlbum.setMaxWidth(albumCol.getPrefWidth());
        final TextField addYear = new TextField();   
        addYear.setPromptText("Song Year");
        addYear.setMaxWidth(yearCol.getPrefWidth());
		
		final Button addButton = new Button("Add Entry");
		addButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				data.add(new SongEntry(
						addNewSong.getText(),
						addArtist.getText(),
						addAlbum.getText(),
						addYear.getText()));
				addNewSong.clear();
				addArtist.clear();
				addAlbum.clear();
				addYear.clear();
					
				}
			});
		
        hb.getChildren().addAll(addNewSong,addArtist,addAlbum,addYear,addButton);
        hb.setSpacing(3);
		
		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10,0,0,10));
		vbox.getChildren().addAll(label,table,hb);
		((Group) scene.getRoot()).getChildren().addAll(vbox);
		
		stage.setScene(scene);
		stage.show();
		
		
	}
	private TableView table = new TableView();
	private final ObservableList<SongEntry> data = FXCollections.observableArrayList(new SongEntry("Rooftops","Lostprophets","Liberation Transmission","2006"));
	final HBox hb = new HBox();
	public static void main(String[] args) {
		launch(args);
	}
	public static class SongEntry{
		
		private final SimpleStringProperty song;
		private final SimpleStringProperty artist;
		private final SimpleStringProperty album;
		private final SimpleStringProperty year;
		
		private SongEntry(String ssong, String sartist, String salbum, String syear){
			this.song = new SimpleStringProperty(ssong);
			this.artist = new SimpleStringProperty(sartist);
			this.album = new SimpleStringProperty(salbum);
			this.year = new SimpleStringProperty(syear);
			
		}
		 public String getSong() {
	            return song.get();
	        }
	 
	        public void setSong(String ssong) {
	            song.set(ssong);
	        }

	        public String getArtist() {
	            return artist.get();
	        }
	 
	        public void setArtist(String sartist) {
	            artist.set(sartist);
	        }
	 
	        public String getAlbum() {
	            return album.get();
	        }
	 
	        public void setAlbum(String salbum) {
	            album.set(salbum);
	        }

	        public String getYear() {
	            return year.get();
	        }
	 
	        public void setYear(String syear) {
	            year.set(syear);
	        }
		
	}
}
