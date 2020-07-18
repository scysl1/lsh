package p4_group_8_repo.Actors;

/**
 * The obstacle which will cause frog death
 */
public class Obstacle extends physicalstuff {
	@Override
	public void act(long now) {
		super.physicalAct(now, 50);
	}

}
