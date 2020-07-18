package p4_group_8_repo.Actors;

/**
 * The log for frog to take
 */
public class Log extends physicalstuff {

	@Override
	public void act(long now) {
		move(getSpeed() , 0);
		if (getX()>600 && getSpeed()>0)
			setX(-180);
		if (getX()<-300 && getSpeed()<0)
			setX(700);
	}

	/**
	 * Determine if the log is going left
	 * @return true if the log is going left
	 */
	public boolean getLeft() {
		return getSpeed() < 0;
	}
}
