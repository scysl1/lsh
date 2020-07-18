package p4_group_8_repo.Actors;
import javafx.scene.image.Image;

/**
 * wet turtles can sink
 */
public class WetTurtle extends Turtles{

	private boolean sunk = false;

	@Override
	public void act(long now) {
		super.physicalAct(now, 75);
	}

	/**
	 * An override method to set different pictures for different conditions
	 * This method which replace the complex condition with variables can make things become clear
	 * @param now the current time
	 */
	@Override
	public void set_Image(long now){
		final boolean by_four_remaining_zero = (now/magicnumber  % 4 ==0);
		final boolean by_four_remaining_one = (now/magicnumber  % 4 ==1);
		final boolean by_four_remaining_two = (now/magicnumber  % 4 ==2);
		final boolean by_four_remaining_three = (now/magicnumber  % 4 ==3);

		turtle1 = new Image(picpath+"TurtleAnimation1.png", getW(), getH(), true, true);
		turtle2 = new Image(picpath+"TurtleAnimation2Wet.png", getW(), getH(), true, true);
		turtle3 = new Image(picpath+"TurtleAnimation3Wet.png", getW(), getH(), true, true);
		turtle4 = new Image(picpath+"TurtleAnimation4Wet.png", getW(), getH(), true, true);


		if (by_four_remaining_zero) {
			setImage(turtle2);
			sunk = false;
		}
		else if (by_four_remaining_one) {
			setImage(turtle1);
			sunk = false;
		}
		else if (by_four_remaining_two) {
			setImage(turtle3);
			sunk = false;
		} else if (by_four_remaining_three) {
			setImage(turtle4);
			sunk = true;
		}
	}

	boolean isSunk() {
		return sunk;
	}
}
