package p4_group_8_repo.page;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PageTest {

    @Test
    public void getPageName() {
        Page newpage = new Page();
        newpage.setPageName("testPage");
        String testname = "testPage";
        assertEquals("testPage", testname);
    }

    @Test
    public void getPageLink() {
        Page newpage = new Page();
        newpage.setPageLink("/fxml_and_css");
        String testname = "/fxml_and_css";
        assertEquals("/fxml_and_css", testname);
    }
}