package p4_group_8_repo.initialize;

/**
 * The controller of initialisation which can modify the attributes
 */
public class Initialisation_controller {
    private Initialisation initialisation;
    private Initialisation_viewer viewer;
    public Initialisation_controller(Initialisation initialisation, Initialisation_viewer viewer){
        this.initialisation = initialisation;
        this.viewer = viewer;
    }
    public int GetNumber_turtle() {
        return initialisation.getNumber_turtle();
    }

    public void SetNumber_turtle(int number_turtle) {
        initialisation.setNumber_turtle(number_turtle);
    }

    public int GetInterval_turtle() {
        return initialisation.getInterval_turtle();
    }

    public void SetInterval_turtle(int interval_turtle) {
        initialisation.setInterval_turtle(interval_turtle);
    }

    public int GetSpeed_trutle() {
        return initialisation.getSpeed_trutle();
    }

    public void SetSpeed_trutle(int speed_trutle) {
        initialisation.setSpeed_trutle(speed_trutle);
    }

    public int GetNumber_log() {
        return initialisation.getNumber_log();
    }

    public void SetNumber_log(int number_log) {
        initialisation.setNumber_log(number_log);
    }

    public int GetInterval_log() {
        return initialisation.getInterval_log();
    }

    public void SetInterval_log(int interval_log) {
        initialisation.setInterval_log(interval_log);
    }

    public double GetSpeed_log() {
        return initialisation.getSpeed_log();
    }

    public void SetSpeed_log(double speed_log) {
        initialisation.setSpeed_log(speed_log);
    }

    public int GetNumber_obstacle() {
        return initialisation.getNumber_obstacle();
    }

    public void SetNumber_obstacle(int number_obstacle) {
        initialisation.setNumber_obstacle(number_obstacle);
    }

    public int GetInterval_obstacle() {
        return initialisation.getInterval_obstacle();
    }

    public void SetInterval_obstacle(int interval_obstacle) {
        initialisation.setInterval_obstacle(interval_obstacle);
    }

    public int GetSpeed_obstacle() {
        return initialisation.getSpeed_obstacle();
    }

    public void SetSpeed_obstacle(int speed_obstacle) {
        initialisation.setSpeed_obstacle(speed_obstacle);
    }

    public int GetSpeed_snake() {
        return initialisation.getSpeed_snake();
    }

    public void SetSpeed_snake(int speed_snake) {
        initialisation.setSpeed_snake(speed_snake);
    }

    /**
     * Print out the parameter of this level of game
     */
    public void updateMessage(){
        viewer.showLevelMessage(GetNumber_turtle(),GetInterval_turtle(),
                GetSpeed_trutle(),GetNumber_log(),GetInterval_log(),
                       GetSpeed_log(),GetNumber_obstacle(),GetInterval_obstacle(),
                            GetSpeed_obstacle(),GetSpeed_snake());
        System.out.println();
    }

}
