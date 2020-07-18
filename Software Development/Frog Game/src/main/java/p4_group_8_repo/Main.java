package p4_group_8_repo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *	The main class for the game
 *  @author Shihong LIU 20029946
 *	Today is 20191124
 */
public class Main extends Application {

	public static void main(String[] args) { launch(args); }

	@Override
	public void start(Stage primaryStage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("/MenuPage.fxml"));
		Application.setUserAgentStylesheet(Application.STYLESHEET_CASPIAN);
		Scene scene1  = new Scene(root,600,800);
		primaryStage.setScene(scene1);
		primaryStage.setResizable(false);
		primaryStage.show();

	}



}
