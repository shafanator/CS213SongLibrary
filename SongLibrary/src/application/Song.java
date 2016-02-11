//Benjamin Bancala and Michael Shafran
//Software Methodology Spring 2016

package application;

public class Song implements java.io.Serializable {
	public String song;
	public String artist;
	public String album;
	public String year;
	
	public Song(String songName,String artistName, String albumName, String yearName){
		song = songName;
		artist = artistName;
		album = albumName;
		year = yearName;
		
	}
	
	@Override
	public String toString(){
		return String.format(this.song);
	}
}
