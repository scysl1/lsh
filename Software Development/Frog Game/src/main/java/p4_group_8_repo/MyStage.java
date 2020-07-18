package p4_group_8_repo;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import p4_group_8_repo.Actors.World;

/**
 * The stage which start to play music and stop it
 */
public class MyStage extends World {

	private MediaPlayer mediaPlayer;
	@Override
	public void act(long now) {}
	public MyStage() {}

	/**
	 * start to play the music from the given path
	 */
	public void playMusic() {
		String musicFile = "src/main/java/p4_group_8_repo/music/Frogger Main Song Theme (loop).mp3";
		Media sound = new Media(new File(musicFile).toURI().toString());
		mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
	    mediaPlayer.play();
	}

	/**
	 * stop playing the music
	 */
	public void stopMusic() {
		mediaPlayer.stop();
	}

}
