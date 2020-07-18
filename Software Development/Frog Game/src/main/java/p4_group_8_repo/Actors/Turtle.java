package p4_group_8_repo.Actors;
import javafx.scene.image.Image;

/**
 * turtle that will not sunk
 */
public class Turtle extends Turtles{


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

		turtle1 = new Image(picpath+"TurtleAnimation1.png", getW(), getH(), true, true);
		turtle2 = new Image(picpath+"TurtleAnimation2.png", getW(), getH(), true, true);
		turtle3 = new Image(picpath+"TurtleAnimation3.png", getW(), getH(), true, true);
	    final boolean by_three_remaining_zero = (now/magicnumber  % 3 ==0);
        final boolean by_three_remaining_one = (now/magicnumber  % 3 ==1);
        final boolean by_three_remaining_two = (now/magicnumber  % 3 ==2);

		if (by_three_remaining_zero) {
			setImage(turtle2);

		}
		else if (by_three_remaining_one) {
			setImage(turtle1);

		}
		else if (by_three_remaining_two) {
			setImage(turtle3);

		}
	}


}
