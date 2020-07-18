package p4_group_8_repo.Actors;

import javafx.scene.image.Image;

/**
 * The snake which will cause frog death
 */
public class Snake extends physicalstuff {

    public void act(long now) {
        super.physicalAct(now, 50);
    }

    /**
     * An override method to set different pictures for different conditions
     * This method which replace the complex condition with variables can make things become clear
     * @param now the current time
     */
    public void set_Image(long now){

        Image snake1 = new Image(picpath+"Snake1.png", getW(), getH(), true, true);
        Image snake2 = new Image(picpath+"Snake2.png", getW(), getH(), true, true);
        Image snake3 = new Image(picpath+"Snake3.png", getW(), getH(), true, true);
        final boolean by_three_remaining_zero = (now/magicnumber  % 3 ==0);
        final boolean by_three_remaining_one = (now/magicnumber  % 3 ==1);
        final boolean by_three_remaining_two = (now/magicnumber  % 3 ==2);

        if (by_three_remaining_zero) {
            setImage(snake1);

        }
        else if (by_three_remaining_one) {
            setImage(snake2);

        }
        else if (by_three_remaining_two) {
            setImage(snake3);

        }
    }
}
