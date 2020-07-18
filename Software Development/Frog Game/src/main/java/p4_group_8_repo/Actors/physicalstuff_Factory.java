package p4_group_8_repo.Actors;

/**
 * The factory class which get the name and creat corresponding instance
 */
public class physicalstuff_Factory {
    /**
     * This is a factory class of factory design pattern.
     * The factory initialise physical stuff according to the input name
     * @param physicalstuff_type the type(name) of the physical stuff to initialize
     * @return an instantiation of a type of physical stuff
     */
    public physicalstuff_initialisation get_physicalstuff_initialisation(String physicalstuff_type) {
        if (physicalstuff_type == null) {
            return null;
        }
        if (physicalstuff_type.equalsIgnoreCase("Log")) {
            return new Log();
        }
        if (physicalstuff_type.equalsIgnoreCase("Obstacle")) {
            return new Obstacle();
        }
        if (physicalstuff_type.equalsIgnoreCase("Turtle")) {
            return new Turtle();
        }
        if (physicalstuff_type.equalsIgnoreCase("WetTurtle")) {
            return new WetTurtle();
        }
        if (physicalstuff_type.equalsIgnoreCase("Snake")) {
            return new Snake();
        }
        return null;
    }
}
