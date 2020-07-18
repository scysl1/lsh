package p4_group_8_repo.Actors;
import javafx.scene.image.Image;

/**
 * The end for frog
 */
public class End extends Actor {
	private boolean activated = false;
	@Override
	public void act(long now) {}
	
	public End(int x, int y) {
		setX(x);
		setY(y);
		setImage(new Image(picpath+"End.png", 60, 60, true, true));
	}
	
	void setEnd() {
		setImage(new Image(picpath+"FrogEnd.png", 70, 70, true, true));
		activated = true;
	}

	/**
	 * Determine if the end is activated
	 * @return true if it is activated
	 */
	boolean isActivated() {
		return activated;
	}
	

}
