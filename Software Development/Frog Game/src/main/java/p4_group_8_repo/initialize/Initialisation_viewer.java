package p4_group_8_repo.initialize;

/**
 * The class will print out the parameter of game
 */
public class Initialisation_viewer {

    public void showLevelMessage(int number_turtle, int interval_turtle, int speed_trutle,
                                 int number_log, int interval_log, double speed_log, int number_obstacle, int interval_obstacle, int speed_obstacle, int speed_snake){
        System.out.println("The number_turtle is:" + number_turtle);
        System.out.println("The interval_turtle is:" + interval_turtle);
        System.out.println("The speed_trutle is:" + speed_trutle);
        System.out.println("The number_log is:" + number_log);
        System.out.println("The interval_log is:" + interval_log);
        System.out.println("The speed_log is:" + speed_log);
        System.out.println("The number_obstacle is:" + number_obstacle);
        System.out.println("The interval_obstacle is:" + interval_obstacle);
        System.out.println("The speed_obstacle is:" + speed_obstacle);
        System.out.println("The speed of snake is: " + speed_snake);


    }
}
