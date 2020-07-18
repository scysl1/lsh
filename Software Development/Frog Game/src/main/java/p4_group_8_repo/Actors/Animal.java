package p4_group_8_repo.Actors;
import java.util.*;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

/**
 * The frog of its behaviour and attributes
 */
public class Animal extends Actor {
	/**
	 * The image of frog movement.
	 */
	private Image imgW1;
	private Image imgA1;
	private Image imgS1;
	private Image imgD1;
	private Image imgW2;
	private Image imgA2;
	private Image imgS2;
	private Image imgD2;
	/**
	 * The score is initialised as zero
	 */
	private int points = 0;
	private int end = 0;
	private int carD = 0;
	/**
	 * The number of lives is initialised as three
	 */
	private int life = 3;
	private double movement = 13.3333333*2;
	private double movementX = 10.666666*2;
	private int imgSize = 40;
	private double w = 800;
	private boolean second = false;
	private boolean noMove = false;
	private boolean carDeath = false;
	private boolean waterDeath = false;
	private boolean changeScore = false;
	private boolean changeLife = false;


	private ArrayList<End> inter = new ArrayList<End>();
	public Animal(String imageLink) {

        setFrogImage();
        setImage(new Image(imageLink, imgSize, imgSize, true, true));
		goToStartPoint();
		keyboard_action();

	}

    /**
     * Set different image of the frog
     */
    private void setFrogImage() {
        imgW1 = getImage("froggerUp.png");
        imgA1 = getImage("froggerLeft.png");
        imgS1 = getImage("froggerDown.png");
        imgD1 = getImage("froggerRight.png");
        imgW2 = getImage("froggerUpJump.png");
        imgA2 = getImage("froggerLeftJump.png");
        imgS2 = getImage("froggerDownJump.png");
        imgD2 = getImage("froggerRightJump.png");
    }

    /**
	 * The act of frog according to current time
	 * @param now the current time
	 */
	@Override
	public void act(long now) {
		int ybounds1 = 0;
		int ybongds2 = 734;
		if (getY()<ybounds1 || getY()>ybongds2) {
			goToStartPoint();
		}
		if (getX()<0) {
			move(movement*2, 0);
		}
		death_action(now);
		decision_judge();
	}

	/**
	 * If the frog is dead, create visual effect and change score.
	 * @param now the current time
	 */
	private void death_action(long now) {
		if (carDeath) {
			noMove = true;
			incrementOfCarD(now);
			death(1, "cardeath1.png");
			death(2, "cardeath2.png");
			death(3, "cardeath3.png");
			if (carD == 4) {
				goToStartPoint();
				carDeath = false;
				carD = 0;
				setImage(getImage("froggerUp.png"));
				noMove = false;
				if (points>50) {
					points-=50;
					changeScore = true;
				}
			}
		}
		if (waterDeath) {
			noMove = true;
			incrementOfCarD(now);
			death(1, "waterdeath1.png");
			death(2, "waterdeath2.png");
			death(3, "waterdeath3.png");
			death(4, "waterdeath4.png");

			if (carD == 5) {

				goToStartPoint();
				waterDeath = false;
				carD = 0;
				setImage(getImage("froggerUp.png"));
				noMove = false;
				if (points>50) {
					points-=50;
					changeScore = true;
				}
			}

		}
	}

	/**
	 * Change the value of carD according to the current time
	 * @param now the current time
	 */
	private void incrementOfCarD(long now) {
		if ((now) % 11 == 0) {
			carD++;
		}
	}

	/**
	 * Conduct different action according to the object frog intersect with
	 */
	private void decision_judge() {
		if (getX()>600) {
			move(-movement*2, 0);
		}
		if (getIntersectingObjects(Obstacle.class).size() >= 1) {
			isCarDeath();
		}
		if (getX() == 240 && getY() == 82) {
			boolean stop = true;
		}
		if (getIntersectingObjects(Log.class).size() >= 1 && !noMove) {
			if(getIntersectingObjects(Log.class).get(0).getLeft())
				move(getIntersectingObjects(Log.class).get(0).getSpeed(),0);
			else
				move(getIntersectingObjects(Log.class).get(0).getSpeed(),0);
		}
		else if (getIntersectingObjects(Turtle.class).size() >= 1 && !noMove) {
			move(getIntersectingObjects(Turtle.class).get(0).getSpeed(),0);
		}
		else if (getIntersectingObjects(WetTurtle.class).size() >= 1) {
			if (getIntersectingObjects(WetTurtle.class).get(0).isSunk()) {
				isWaterDeath();

			} else {
				move(getIntersectingObjects(WetTurtle.class).get(0).getSpeed(),0);
			}
		}
		else if(getIntersectingObjects(Snake.class).size() >= 1){
			isSnakeDeath();
		}
		else if (getIntersectingObjects(End.class).size() >= 1) {
			inter = (ArrayList<End>) getIntersectingObjects(End.class);
			if (getIntersectingObjects(End.class).get(0).isActivated()) {
				end--;
				points-=50;
			}
			points+=50;
			changeScore = true;
			w=800;
			getIntersectingObjects(End.class).get(0).setEnd();
			end++;
			goToStartPoint();

		}
		else if (getY()<413){
			isWaterDeath();
		}
	}

	/**
	 * If hit car, lose a life
	 */
	private void isCarDeath() {
		carDeath = true;
		lose_life();
	}
	/**
	 * If fall into water, lose a life
	 */
	private void isWaterDeath() {
		waterDeath = true;
		lose_life();
	}
	/**
	 * If hit the snake, lose a life
	 */
	private void isSnakeDeath() {
		lose_life();
	}

	/**
	 * According to the carD value, set different image of death
	 * @param i the number of carD
	 * @param s The image path
	 */
	private void death(int i, String s) {
		if (carD == i) {
			setImage(getImage(s));
		}
	}

	/**
	 * The action response to the keyboard action. According to different keyboard action, it will call different frog action
	 */
	private void keyboard_action() {
		setOnKeyPressed(event -> {
			if (noMove) {}
			else {
				final boolean press_W = (event.getCode() == KeyCode.W);
				final boolean press_A = (event.getCode() == KeyCode.A);
				final boolean press_S = (event.getCode() == KeyCode.S);
				final boolean press_D = (event.getCode() == KeyCode.D);
				if (second) {
				if (press_W) {
					frog_action(0, -movement, imgW1, false);
					changeScore = false;
				}
				else if (press_A) {
					frog_action(-movementX, 0, imgA1, false);
				}
				else if (press_S) {
					frog_action(0, movement, imgS1, false);
				}
				else if (press_D) {
					frog_action(movementX, 0, imgD1, false);
				}
			}
			else if (press_W) {
					frog_action(0, -movement, imgW2, true);
				}
			else if (press_A) {
					frog_action(-movementX, 0, imgA2, true);
				}
			else if (press_S) {
					frog_action(0, movement, imgS2, true);
				}
			else if (press_D) {
					frog_action(movementX, 0, imgD2, true);
				}
		}
		});
		setOnKeyReleased(event -> {
			final boolean press_W = (event.getCode() == KeyCode.W);
			final boolean press_A = (event.getCode() == KeyCode.A);
			final boolean press_S = (event.getCode() == KeyCode.S);
			final boolean press_D = (event.getCode() == KeyCode.D);
			if (noMove) {}
			else {
			if (press_W) {
				if (getY() < w) {
					changeScore = true;
					w = getY();
					points+=10;
				}
				frog_action(0, -movement, imgW1, false);
			}
			else if (press_A) {
				frog_action(-movementX, 0, imgA1, false);
			}
			else if (press_S) {
				frog_action(0, movement, imgS1, false);
			}
			else if (press_D) {
				frog_action(movementX, 0, imgD1, false);
			}
		}
		});
	}

	/**
	 * action of frog response to the keyboard action
	 * @param i xpos change
	 * @param v ypos change
	 * @param imgW1 set the image
	 * @param b boolean value
	 */
	private void frog_action(double i, double v, Image imgW1, boolean b) {
		move(i, v);
		setImage(imgW1);
		second = b;
	}

	/**
	 * Get the image from given path
	 * @param s the path to the image
	 * @return an Image with given size
	 */
	public Image getImage(String s) {
		return new Image(picpath + s, imgSize, imgSize, true, true);
	}

	/**
	 * Help determine if the frog has occupied all 5 ends
	 * @return true if all ends were occupied
	 */
	public boolean getStop() {
		return end==5;
	}

	/**
	 * Get the current point
	 * @return the points
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * Back to the start point
	 */
	private void goToStartPoint(){
		setX(300);
		double setYnum = 679.8;
		setY(setYnum +movement);
	}

	/**
	 * Determine whether the score is changed
	 * @return true if the score is changed, otherwise return false
	 */
	public boolean changeScore() {
		if (changeScore) {
			changeScore = false;
			return true;
		}
		return false;
	}

	/**
	 * Get the number of remaining lives
	 * @return the number of lives
	 */
	public int getLife(){
		return this.life;
	}
	/**
	 * Determine whether the number of lives is changed
	 * @return true if the number of lives is changed, otherwise return false
	 */
	public boolean changeLife() {
		if (changeLife) {
			changeLife = false;
			return true;
		}
		return false;
	}

	/**
	 * Determine whether no more life remaining
	 * @return true if all lives were lost
	 */
	public boolean noMoreLife(){
		return this.life == 0;
	}

	/**
	 * If the animal lost a life, change score and create the alert
	 */
	private void lose_life(){
		if (this.life >1){
			this.life -= 1;
			changeLife = true;
			goToStartPoint();
			show_temp_score();
		} else {
			changeLife = true;
			goToStartPoint();
			this.life = 0;
		}

	}

	/**
	 * Show the score of current round
	 */
	private void show_temp_score() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("You lost a life!");
		alert.setHeaderText("your score is: " + getPoints());
		alert.setContentText("Try again!");
		alert.show();
	}


}
