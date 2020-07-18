package p4_group_8_repo.page;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The controller of page, which can set and adjust its attributes
 */
public class Page_controller {

    private Page page;
    private Page_viewer view;

    public Page_controller(Page page, Page_viewer view){
        this.page = page;
        this.view = view;
    }

    public String GetPageName(){
        return page.getPageName();
    }
    public void setPageName(String page_name){
        page.setPageName(page_name);
    }
    public String GetPageLink(){
        return page.getPageLink();
    }
    public void setPageLink(String page_link){
        page.setPageLink(page_link);
    }
    public void updatePageInfo(){
        view.showPageMessage(GetPageName(),GetPageLink());
    }

    /**
     * This method can change page in the same stage according to the given path and event.
     * @param event receive event from previous file
     * @param s the path to the fxml
     * @throws IOException throws input/output exception
     */
    public void ShowPage(ActionEvent event, String s) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(s));
        Scene menu = new Scene(root, 600, 800);
        Stage view_menu = (Stage) ((Node) event.getSource()).getScene().getWindow();
        view_menu.setScene(menu);
        view_menu.show();
    }
}
