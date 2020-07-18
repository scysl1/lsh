package p4_group_8_repo.initialize;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import p4_group_8_repo.Actors.Animal;
import p4_group_8_repo.Actors.Digit;
import p4_group_8_repo.MyStage;
import p4_group_8_repo.page.Page;
import p4_group_8_repo.page.Page_controller;
import p4_group_8_repo.page.Page_viewer;

import java.io.IOException;

/**
 * The class which can modify the score according to the timer
 */
public class initialisation_start_score_timer {
    public MyStage background;
    private AnimationTimer timer;
    private Animal animal;

    /**
     * Start the timer and the animal(frog)
     * @param background receive the given background
     * @param timer receive the given timer
     * @param animal receive the animal
     */
    initialisation_start_score_timer(MyStage background, AnimationTimer timer, Animal animal){
        this.background = background;
        this.timer = timer;
        this.animal = animal;
    }

    /**
     * Start the game, play the music, and start the timer
     */
     void start() {
        background.playMusic();
        createTimer();
        timer.start();
     }

    /**
     * Stop the timer
     */
    public void stop() {
        timer.stop();
    }

    /**
     * Create a timer for score calculation
     */
    public void createTimer() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (animal.changeScore()) {
                    setNumber(animal.getPoints());
                }
                if (animal.changeLife()){
                    setLife(animal.getLife());
                }
                if (animal.noMoreLife()){
                    background.stopMusic();
                    stop();
                    background.stop();
                    Lose_info();
                }
                if (animal.getStop()) {
                    System.out.print("STOP:");
                    background.stopMusic();
                    stop();
                    background.stop();
                    try {
                        createAlert();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    /**
     * Create an alert with lose information
     */
    private void Lose_info() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("You lose!");
        alert.setHeaderText("your score is: " + animal.getPoints());
        alert.setContentText("Game over");
        alert.show();
    }

    /**
     * Add a back button to menu page on the right corner.
     */
    void  InitializeButton() {
        Button btn = new Button("Back");
        btn.setLayoutX(550);
        btn.setLayoutY(60);
        background.getChildren().add(btn);
        btn.setOnAction(event -> {
            try {
                setMenuPage(event);
                background.stopMusic();
                stop();
                background.stop();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    /**
     * This method is designed for change the page to the menu page
     * @param event the event received from button
     * @throws IOException throw exception if it receives useless
     */
    private void setMenuPage(ActionEvent event) throws IOException {
        Page change_page = new Page();
        Page_viewer viewer = new Page_viewer();
        Page_controller page_controller = new Page_controller(change_page, viewer);
        page_controller.setPageName("MenuPage");
        page_controller.setPageLink("/MenuPage.fxml");
        page_controller.ShowPage(event, page_controller.GetPageLink());
    }

    /**
     * The method create the alert with score
     * @throws IOException throws input/output exception
     */
    private void createAlert() throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("You Have Won The Game!");
        alert.setHeaderText("Your High Score: "+animal.getPoints()+"!");
        alert.setContentText("Highest Possible Score: 800");
        write_view_score(animal.getPoints());
        alert.show();
    }

    /**
     * This method is to print and save the score
     * @param animal_score the score of game
     * @exception IOException throws input/output exception
     */
    private void write_view_score(int animal_score) throws IOException {
        Write_view_score score = Write_view_score.getInstance();
        score.writeScore(animal_score);
        score.viewScore();
    }

    /**Change the score number according to the point got
     * @param n game points
     */
    public void setNumber(int n) {
        int shift = 0;
        while (n > 0) {
            int d = n / 10;
            int k = n - d * 10;
            n = d;
            background.add(new Digit(k, 30, 550 - shift, 25));
            shift += 30;
        }
    }

    /**
     * Display the life at the bottom
     * @param number_life the number of life
     */
    private void setLife(int number_life){
        int shift = 45;
        for(int i= 0; i<3-number_life; i++){
            background.add(new Digit("file:src/main/java/p4_group_8_repo/pic/"+ "death.png", shift, 250 + i * shift, 745));
        }
    }

}

