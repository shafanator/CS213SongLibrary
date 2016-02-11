//Benjamin Bancala and Michael Shafran
//Software Methodology Spring 2016

package application;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Controller {
	@FXML
	ListView<Song> listview;
	@FXML
	TextField textSong;
	@FXML
	TextField textArtist;
	@FXML
	TextField textAlbum;
	@FXML
	TextField textYear;
	@FXML
	Label labelSong;
	@FXML
	Label labelArtist;
	@FXML
	Label labelAlbum;
	@FXML
	Label labelYear;
	@FXML
	Button buttonAdd;
	@FXML
	Button buttonDelete;
	@FXML
	Label textSongRequired;
	@FXML
	Label textArtistRequired;
	@FXML
	Button buttonEdit;
	@FXML
	Button buttonUpdate;
	
	
	private ObservableList<Song> obsList;
	private ObservableList<Song> tempList;
	String songName;
	String artistName;
	
	Song song1;
	
	
	
	public void start(Stage mainStage){
			buttonUpdate.visibleProperty().set(false);
			obsList = FXCollections.observableArrayList();
			tempList = FXCollections.observableArrayList();
			File fi = new File("song.ser");
			if(fi.exists()&&!fi.isDirectory()){
					deSer();
					labelSong.setText(obsList.get(0).song);
					labelArtist.setText(obsList.get(0).artist);
					labelAlbum.setText(obsList.get(0).album);
					labelYear.setText(obsList.get(0).year);
			}
			
			listview.setItems(obsList);
			listview.getSelectionModel().select(0);
			listview.getSelectionModel().selectedItemProperty().addListener((obs,oldVal,newVal)->showItemInputDialog(mainStage));
			
			/*obsList.add(song1);
			listview.setItems(obsList);*/
			
			
			buttonAdd.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event){
					boolean sameSong = false;
					boolean sameArtist = false;
					Song song2 = new Song("","","","");
					if((textSong.getText().toString()).equals("")){
						textSongRequired.setText("Required");
					}else{
						if((textArtist.getText().toString()).equals("")){
							textArtistRequired.setText("Required");
						}else{
							
							for(int i = 0; i<obsList.size();i++){
								if((textSong.getText().toString()).equals(obsList.get(i).song)&&((textArtist.getText().toString()).equals(obsList.get(i).artist))){
									sameSong = true;
									sameArtist = true;
								}
								
							}
							
							if(sameSong ==  true&& sameArtist ==true){
								textSongRequired.setText("Exists");
								
							}else{
								song2.song = textSong.getText().toString().trim();
								song2.artist=textArtist.getText().toString().trim();
								song2.album=textAlbum.getText().toString().trim();
								song2.year=textYear.getText().toString().trim();
								songName = song2.song;
								artistName = song2.artist;
								song1 = song2;
								obsList.add(song2);
								listview.setItems(obsList);
								textSongRequired.setText("");
								textArtistRequired.setText("");
								textSong.clear();
								textArtist.clear();
								textAlbum.clear();
								textYear.clear();
								textSongRequired.setText("");
							}
							
						}
						
					}
					if(obsList.size()>1){
						int minIndex;
						String temp1="";
						String temp2="";
						for(int i = 0; i<obsList.size()-1;i++){
							minIndex = i;
							for(int j = i+1; j<obsList.size();j++){
								temp1 = obsList.get(j).song;
								temp2 = obsList.get(minIndex).song;
								if(temp1.compareToIgnoreCase(temp2)<0){
									minIndex = j;
								}
							}
							tempList.add(obsList.get(i));
							obsList.set(i, obsList.get(minIndex));
							obsList.set(minIndex, tempList.get(0));
							tempList.remove(0);
						}
					}
					 
					int x=0;
					for(int i = 0; i<obsList.size();i++){
						if((songName.equals(obsList.get(i).song))&&(artistName.equals(obsList.get(i).artist))){
							x=i;
						}
					}
					listview.getSelectionModel().clearAndSelect(x);

					listview.getSelectionModel().select(x);
					
				}
					
			});
			
			buttonDelete.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event){
					int x=0;
					song1 = listview.getSelectionModel().getSelectedItem();	
					for(int i = 0; i< obsList.size();i++){
						if(song1.equals(obsList.get(i))){
							x=i;
						}
					}
					obsList.remove(song1);
					if(x!=obsList.size()){
						listview.getSelectionModel().clearAndSelect(x);
						
					}else{
						listview.getSelectionModel().clearAndSelect(x-1);
					}
					
					listview.refresh();
				}
			});
			buttonEdit.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event){
					if(song1!=null){
						buttonEdit.visibleProperty().set(false);
						buttonUpdate.visibleProperty().set(true);
						song1 = listview.getSelectionModel().getSelectedItem();
						textSong.setText(song1.song);
						textArtist.setText(song1.artist);
						textAlbum.setText(song1.album);
						textYear.setText(song1.year);
					}
					if(obsList.size()>1){
						int minIndex;
						String temp1="";
						String temp2="";
						for(int i = 0; i<obsList.size()-1;i++){
							minIndex = i;
							for(int j = i+1; j<obsList.size();j++){
								temp1 = obsList.get(j).song;
								temp2 = obsList.get(minIndex).song;
								if(temp1.compareToIgnoreCase(temp2)<0){
									minIndex = j;
								}
							}
							tempList.add(obsList.get(i));
							obsList.set(i, obsList.get(minIndex));
							obsList.set(minIndex, tempList.get(0));
							tempList.remove(0);
						}
					}
					
					
				}
			});
			buttonUpdate.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event){
					boolean sameSong = false;
					boolean sameArtist = false;
					if((textSong.getText().toString()).equals("")){
						textSongRequired.setText("Required");
					}else{
						if((textArtist.getText().toString()).equals("")){
							textArtistRequired.setText("Required");
						}else{

							for(int i = 0; i<obsList.size();i++){
								if((textSong.getText().toString()).equals(obsList.get(i).song)&&((textArtist.getText().toString()).equals(obsList.get(i).artist))){
									sameSong = true;
									sameArtist = true;
								}
							}
							
							if(sameSong ==  true&&sameArtist){
								textSongRequired.setText("Exists");
							}else{
								buttonUpdate.visibleProperty().set(false);
								
								buttonEdit.visibleProperty().set(true);
								song1 = listview.getSelectionModel().getSelectedItem();
								
								
								song1.song = textSong.getText().toString().trim();
								song1.artist=textArtist.getText().toString().trim();
								song1.album=textAlbum.getText().toString().trim();
								song1.year=textYear.getText().toString().trim();
								
								songName = song1.song;
								artistName = song1.artist;
								
								textSong.setText("");
								textArtist.setText("");
								textAlbum.setText("");
								textYear.setText("");
								listview.refresh();
								
								textSongRequired.setText("");
							}
							
						}
					}
					if(obsList.size()>1){
						int minIndex;
						String temp1="";
						String temp2="";
						for(int i = 0; i<obsList.size()-1;i++){
							minIndex = i;
							for(int j = i+1; j<obsList.size();j++){
								temp1 = obsList.get(j).song;
								temp2 = obsList.get(minIndex).song;
								if(temp1.compareToIgnoreCase(temp2)<0){
									minIndex = j;
								}
							}
							tempList.add(obsList.get(i));
							obsList.set(i, obsList.get(minIndex));
							obsList.set(minIndex, tempList.get(0));
							tempList.remove(0);
						}
					}
					 
					int x=0;
					for(int i = 0; i<obsList.size();i++){
						if((songName.equals(obsList.get(i).song))&&(artistName.equals(obsList.get(i).artist))){
							x=i;
						}
					}
					listview.getSelectionModel().clearAndSelect(x);
					listview.getSelectionModel().select(x);
					
					
				}
			});
			
			
		
		    mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		       @Override
		       public void handle(WindowEvent e) {
		    	  
		    	   File f = new File("song.ser");
		    	   f.delete();
		    	   if(obsList.size()!=0){
		    		   try {
		    			   f.createNewFile();
							Ser();
		    		   } catch (IOException e1) {
		    			   // TODO Auto-generated catch block
		    			   e1.printStackTrace();
		    		   }
		    		   
		    	   }else{
		    		   
		    	   }
		          System.exit(0);
		       }
		    });
			
	}
	private void Ser(){
		try
	      {
	         FileOutputStream fileOut =
	         new FileOutputStream("song.ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         for(int i = 0;i<obsList.size();i++){
	         	Song temp = obsList.get(i);
	        	out.writeObject(temp);
	         }
	         out.close();
	         fileOut.close();
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	}
	private void deSer(){
		File f = new File("song.ser");
		if (f.exists()&&!f.isDirectory()){
			try{
				FileInputStream fileIn = new FileInputStream("song.ser");
				ObjectInputStream in = new ObjectInputStream(fileIn);
	       
				while (true) {
			        try {
			        	
				        Song e = (Song) in.readObject();
				        obsList.add(e);
				        
			        } catch (EOFException e) {
			            break;
			        } catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			    }
				in.close();
		        fileIn.close();
			} catch(Exception e){
				
			}
			

		}
	    
	}
	private void showItemInputDialog(Stage mainStage){
		song1 = listview.getSelectionModel().getSelectedItem();
		if(song1 != null){
			labelSong.setText(song1.song);
			labelArtist.setText(song1.artist);
			labelAlbum.setText(song1.album);
			labelYear.setText(song1.year);
		}else{
			labelSong.setText("");
			labelArtist.setText("");
			labelAlbum.setText("");
			labelYear.setText("");
		}
		
	}
	
}
