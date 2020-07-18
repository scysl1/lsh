package p4_group_8_repo.initialize;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import p4_group_8_repo.*;
import p4_group_8_repo.Actors.*;

/**
 * This class initialize all elements
 */
public class Initialisation {
    private int number_turtle = 0;
    private int interval_turtle = 0;
    private int speed_trutle = 0;
    private int number_log = 0;
    private int interval_log = 0;
    private double speed_log = 0;
    private int number_obstacle = 0;
    private int interval_obstacle = 0;
    private int speed_obstacle = 0;
    private int speed_snake = 0;
    public MyStage background;
    private AnimationTimer timer;
    private Animal animal;
    public static String picpath = "file:src/main/java/p4_group_8_repo/pic/";
    private physicalstuff_Factory physicalstuff_factory = new physicalstuff_Factory();

    int getNumber_turtle() { return number_turtle; }
    void setNumber_turtle(int number_turtle) {
        this.number_turtle = number_turtle;
    }
    int getInterval_turtle() {
        return interval_turtle;
    }
    void setInterval_turtle(int interval_turtle) {
        this.interval_turtle = interval_turtle;
    }
    int getSpeed_trutle() {
        return speed_trutle;
    }
    void setSpeed_trutle(int speed_trutle) {
        this.speed_trutle = speed_trutle;
    }
    int getNumber_log() {
        return number_log;
    }
    void setNumber_log(int number_log) {
        this.number_log = number_log;
    }
    int getInterval_log() {
        return interval_log;
    }
    void setInterval_log(int interval_log) {
        this.interval_log = interval_log;
    }
    double getSpeed_log() {
        return speed_log;
    }
    void setSpeed_log(double speed_log) {
        this.speed_log = speed_log;
    }
    int getNumber_obstacle() {
        return number_obstacle;
    }
    void setNumber_obstacle(int number_obstacle) {
        this.number_obstacle = number_obstacle;
    }
    int getInterval_obstacle() {
        return interval_obstacle;
    }
    void setInterval_obstacle(int interval_obstacle) {
        this.interval_obstacle = interval_obstacle;
    }
    int getSpeed_obstacle() {
        return speed_obstacle;
    }
    void setSpeed_obstacle(int speed_obstacle) {
        this.speed_obstacle = speed_obstacle;
    }
    public int getSpeed_snake() { return speed_snake; }
    public void setSpeed_snake(int speed_snake) { this.speed_snake = speed_snake; }

    /**
     * This is the main method received call from the main controller
     * @param event receive event from the main controller
     */
    public Initialisation(ActionEvent event) {
        background = new MyStage();
        Scene scenelevel = new Scene(background,600,800);
        Stage level = (Stage)((Node)event.getSource()).getScene().getWindow();
        level.setScene(scenelevel);
        level.show();
    }

    /**
     * After receiving the event, start initialising all game elements
     */
    public void initialisation_start() {

        InitializeBgm();
        InitializeObstacle(number_obstacle, interval_obstacle, speed_obstacle);
        InitializeLog(number_log, interval_log, speed_log);
        InitializeTurtle(number_turtle, interval_turtle, speed_trutle);
        InitializeFrog();
        InitializeEnd();
        InitializeScore();
        InitializeSnake(speed_snake);
        initialisation_start_score_timer start_score_timer = new initialisation_start_score_timer(background,timer,animal);
        start_score_timer.InitializeButton();
        background.start();
        start_score_timer.start();
    }

    /**
     * This is a factory design pattern that initialize different game elements
     * @param physicalstuff_type the type(name) of the elements(belong to physical stuff)
     * @param speed_obstacle the speed
     * @param xpos xpos
     * @param ypos ypos
     * @param w width
     * @param h height
     * @param s picture path
     */
    private void initialize_physicalstuff(String physicalstuff_type, int speed_obstacle, int xpos, int ypos, int w, int h, String s) {
        physicalstuff physicalstuff1 = (physicalstuff) physicalstuff_factory.get_physicalstuff_initialisation(physicalstuff_type);
        physicalstuff1.setSpeed(speed_obstacle);
        physicalstuff1.setXpos(xpos);
        physicalstuff1.setYpos(ypos);
        physicalstuff1.setW(w);
        physicalstuff1.setH(h);
        physicalstuff1.set_Image(picpath + s, physicalstuff1.getW(), physicalstuff1.getH());
        background.add(physicalstuff1);
    }

    /**
     * Initialize different obstacles using factory design pattern
     * @param number_obstacle the number of obstacle
     * @param interval_obstacle the interval between obstacles
     * @param speed_obstacle speed of obstacles
     */
    public void InitializeObstacle(int number_obstacle, int interval_obstacle, int speed_obstacle){

        for(int i = 0; i < number_obstacle; i++) {
            initialize_physicalstuff("Obstacle", speed_obstacle, i * interval_obstacle, 649, 150,150, "/truck1Right.png");
        }
        for(int i = 0; i < number_obstacle+1; i++) {
            initialize_physicalstuff("Obstacle",-2*(speed_obstacle), (i*interval_obstacle/2)-100, 597, 50,50, "/car1Left.png");
            initialize_physicalstuff("Obstacle",-speed_obstacle, (i*interval_obstacle)*5/3-100, 485, 50,50, "/redcar.png");

        }
        for(int i = 0; i < number_obstacle - 1 ; i++) {
            initialize_physicalstuff("Obstacle",speed_obstacle, (i*interval_obstacle)*5/3, 540, 150,150, "/truck1Right.png");
        }

    }

    /**
     * Initialized different log using factory design pattern
     * @param number_log the number of log
     * @param interval_between_log the interval between los
     * @param speed_log the speed of log
     */
    private void InitializeLog(int number_log, int interval_between_log, double speed_log){

        for (int i = 0; i < number_log; i++) {
            initialize_physicalstuff("Log", (int) speed_log, i * interval_between_log, 166, 150,150, "/log3.png");
            initialize_physicalstuff("Log", (int) speed_log, 50 + i * interval_between_log, 329, 150,150, "/log3.png");
        }
        for (int i = 0; i < number_log - 1; i++) {
            initialize_physicalstuff("Log", (int) (-2 * speed_log), i * 400, 276, 300,300, "/logs.png");
        }

    }

    /**
     * Initialized different turtle and wet turtle using factory design pattern
     * @param number_Turtle the number of turtle
     * @param interval_between_turtle the interval between turtles
     * @param speed_turtle the speed of turtle
     */
    private void InitializeTurtle(int number_Turtle, int interval_between_turtle, int speed_turtle){

        for(int i = 0; i < number_Turtle; i++) {
            initialize_physicalstuff("Turtle", -speed_turtle, 300 + i * interval_between_turtle, 376, 130,130, "/TurtleAnimation2.png");
            initialize_physicalstuff("WetTurtle", -speed_turtle, 200 + i * interval_between_turtle, 217, 130,130, "/TurtleAnimation2.png");
        }

    }

    /**
     * Initialize the snake
     * @param speed_snake the speed of the snake
     */
    private void InitializeSnake(int speed_snake){
        initialize_physicalstuff("Snake", -speed_snake, 550, 420,100,100,"/Snake1.png");
    }
    /**
     * Add a background image
     */
    private void InitializeBgm(){
        BackgroundImage froggerback = new BackgroundImage(picpath+"/iKogsKW.png");
        background.add(froggerback);
    }

    /**
     * Initialized the score digit
     */
    private void InitializeScore(){
        background.add(new Digit(0, 30, 550, 25));
    }

    /**
     * Initialize the ends
     */
    private void InitializeEnd(){
        background.add(new End(13,96));
        background.add(new End(141,96));
        background.add(new End(141 + 141-13,96));
        background.add(new End(141 + 141-13+141-13+1,96));
        background.add(new End(141 + 141-13+141-13+141-13+3,96));
    }

    /**
     * Initialize the frog
     */
    private void InitializeFrog(){
        animal = new Animal(picpath+"/froggerUp.png");
        for(int i = 0; i < 3; i++){
            background.add(new Digit(picpath+ "froggerUp.png", 40, 250 + i * 50, 745));
        }
        background.add(animal);
    }






}
