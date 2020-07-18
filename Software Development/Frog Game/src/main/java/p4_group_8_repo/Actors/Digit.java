package p4_group_8_repo.Actors;

import javafx.scene.image.Image;
import p4_group_8_repo.Actors.Actor;

/**
 * the digit showing the score
 */
public class Digit extends Actor {

	@Override
	public void act(long now) {}

	/**
	 * Set the digit of score
	 * @param n the digit number
	 * @param dim the size of digit
	 * @param x the xpos
	 * @param y the ypos
	 */
	public Digit(int n, int dim, int x, int y) {
		Image im1 = new Image(picpath + n + ".png", dim, dim, true, true);
		setImage(im1);
		setX(x);
		setY(y);
	}

	/**
	 * Set the life digit
	 * @param path the path of lige picture
	 * @param dim the size
	 * @param x xpos
	 * @param y ypos
	 */

	public Digit(String path, int dim, int x, int y){
		Image life = new Image(path, dim, dim, true, true);
		setImage(life);
		setX(x);
		setY(y);
	}

	
}
