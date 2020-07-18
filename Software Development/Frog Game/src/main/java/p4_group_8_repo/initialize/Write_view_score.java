package p4_group_8_repo.initialize;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The class is designed for viewing and writing score from files
 */
public class Write_view_score {

    /**
     * Singleton design pattern for get an instance.
     */
    private static Write_view_score view = new Write_view_score();
    private Write_view_score(){}
    public static Write_view_score getInstance(){
        return view;
    }

    /**
     * Write the score to the given path
     * @param score get the score for further written
     */
    void writeScore(int score){
        try {
            File file = new File("score.txt");
            BufferedWriter output = new BufferedWriter(new FileWriter(file, true));
            output.newLine();
            output.append("").append(String.valueOf(score));
            output.close();

        } catch (IOException ex1) {
            System.out.printf("ERROR writing score to file: %s\n", ex1);
        }
    }

    /**
     * Display the score
     * @throws IOException throws input/output exception
     */
    public void viewScore() throws IOException {

        Stage newstage = new Stage();
        AnchorPane root = FXMLLoader.load(getClass().getResource("/ScorePage.fxml"));
        Scene scorepage = new Scene(root, 300, 400);

        File file = new File("score.txt");
        if(file.isFile() && file.exists()){
            try {
                ArrayList<String> arrayList = new ArrayList<>();
                Read_score_from_file(arrayList);
                int[] array = sort_score(arrayList);
                display_score(root, array);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        newstage.setScene(scorepage);
        newstage.show();
    }

    /**
     * Sort the score
     * @param arrayList an unsorted array containing scores
     * @return return  an sorted array
     */
     int[] sort_score(ArrayList<String> arrayList) {
        int length = arrayList.size();
        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            String s = arrayList.get(i);
            array[i] = Integer.parseInt(s);
        }
        Arrays.sort(array);
        return array;
    }

    /**
     * Read the score from the file
     * @param arrayList an array containing scores of integers
     */
     void Read_score_from_file(ArrayList<String> arrayList) {
        try {
            FileReader fr = new FileReader("score.txt");
            BufferedReader bf = new BufferedReader(fr);
            String str;
            while ((str = bf.readLine()) != null) {
                arrayList.add(str);
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Print the score
     * @param root the root pane
     * @param array the sorted array containing scores
     */
     void display_score(AnchorPane root, int[] array) {
        for(int i = array.length - 1; i>=0; i--){
            int Score = array[i];
            Label text1 = new Label();
            text1.setText(Integer.toString(Score));
            text1.setStyle("-fx-font-size: 20px;-fx-font-family:Courier;-fx-text-alignment: center;");
            text1.setLayoutX(140);
            text1.setLayoutY((array.length-1-i)*20+100);
            root.getChildren().add(text1);
        }
    }
}
