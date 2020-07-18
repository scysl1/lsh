package p4_group_8_repo.page;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Page_controllerTest {
    private Page page = new Page();
    private Page_viewer view = new Page_viewer();
    private Page_controller controller = new Page_controller(page, view);
    @Test
    public void getPageName() {
        controller.setPageName("test_name_controller");
        String test_name = "test_name_controller";
        assertEquals(test_name, controller.GetPageName());
    }

    @Test
    public void getPageLink() {
        controller.setPageLink("test_link_controller");
        String test_link = "test_link_controller";
        assertEquals(test_link, controller.GetPageLink());
    }
}