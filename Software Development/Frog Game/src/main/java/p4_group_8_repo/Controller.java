package p4_group_8_repo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import p4_group_8_repo.initialize.Initialisation;
import p4_group_8_repo.initialize.Initialisation_controller;
import p4_group_8_repo.initialize.Initialisation_viewer;
import p4_group_8_repo.page.Page;
import p4_group_8_repo.page.Page_controller;
import p4_group_8_repo.page.Page_viewer;
import p4_group_8_repo.initialize.Write_view_score;

import java.io.IOException;

/**
 * receive the event from fxml, change levels of game and change pages
 */
public class Controller extends Main {
    /**
     * Once the level1 button is pressed, set the game parameters
     * @param event receive the event from fxml
     */
    @FXML public void onLevel1Pressed(ActionEvent event) {
        setGameParameter(event, 3,200,
                    1,3,220,
                    1,2,300,1,2);
    }
    /**
     * Once the level2 button is pressed, set the game parameters, speed is faster and more obstacles
     * @param event receive the event from fxml
     */
    @FXML public void onLevel2Pressed(ActionEvent event) {
        setGameParameter(event, 2,300,
                2,3,220,
                1,3,200,2,3);

    }
    /**
     * Once the level3 button is pressed, set the game parameters, speed is faster and more obstacles
     * @param event receive the event from fxml
     */
    @FXML public void onLevel3Pressed(ActionEvent event) {
        setGameParameter(event, 2,300,
                2,2,240,
                2,3,200,2,4);
    }
    /**
     * Change to the info page
     * @param event receive the event from the fxml
     * @throws IOException throws input/output exception
     */
    @FXML public void onViewGuidance(ActionEvent event) throws IOException {
        setPage(event, "GuidancePage", "/GuidancePage.fxml");
    }

    /**
     * Change to the menu page
     * @param event receive the event from the fxml
     * @throws IOException throws input/output exception
     */
    @FXML public void onViewMenu(ActionEvent event) throws IOException {
        setPage(event, "MenuPage", "/MenuPage.fxml");
    }

    /**
     * This is a singleton design pattern for writing and viewing scores each time
     * @throws IOException throws input/output exception
     */
    @FXML public void onViewScore() throws IOException {
        Write_view_score view = Write_view_score.getInstance(); view.viewScore();
    }


    /**
     * This is a MVC design pattern which can load game levels of different difficulties and print the information of parameters
     * @param event the event received from FXML
     * @param number_turtle the number of turtle
     * @param interval_turtle the interval between turtles
     * @param speed_trutle the movement speed of turtle
     * @param number_log the number of log
     * @param interval_log the interval between logs
     * @param speed_log the movement speed of log
     * @param number_obstacle the number of obstacle
     * @param interval_obstacle the interval between obstacle
     * @param speed_obstacle the movement speed of obstacle
     * @param speed_snake the speed of snake
     */
    private void setGameParameter(ActionEvent event, int number_turtle, int interval_turtle,
                                  int speed_trutle, int number_log, int interval_log, double speed_log,
                                    int number_obstacle, int interval_obstacle , int speed_obstacle, int speed_snake) {
        Initialisation model = new Initialisation(event);
        Initialisation_viewer view = new Initialisation_viewer();
        Initialisation_controller controller = new Initialisation_controller(model,view);
        controller.SetNumber_log(number_log);
        controller.SetInterval_log(interval_log);
        controller.SetSpeed_log(speed_log);
        controller.SetNumber_turtle(number_turtle);
        controller.SetInterval_turtle(interval_turtle);
        controller.SetSpeed_trutle(speed_trutle);
        controller.SetNumber_obstacle(number_obstacle);
        controller.SetInterval_obstacle(interval_obstacle);
        controller.SetSpeed_obstacle(speed_obstacle);
        controller.SetSpeed_snake(speed_snake);
        model.initialisation_start();
        controller.updateMessage();
    }

    /**
     * This is a MVC design pattern which can set the page and print the page information
     * @param event the event received from FXML
     * @param PageName set the name for the page
     * @param PageLink set the link for the page
     * @throws IOException throw the input/output exceptions
     */
    public void setPage(ActionEvent event, String PageName, String PageLink) throws IOException {
        Page change_page = new Page();
        Page_viewer viewer = new Page_viewer();
        Page_controller page_controller = new Page_controller(change_page, viewer);
        page_controller.setPageName(PageName);
        page_controller.setPageLink(PageLink);
        page_controller.ShowPage(event, page_controller.GetPageLink());
        page_controller.updatePageInfo();
    }

}
