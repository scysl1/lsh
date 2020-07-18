package p4_group_8_repo.page;

/**
 * The class contains attributes of page name and link
 */
public class Page {

    private String page_link;
    private String page_name;

    String getPageName() {
        return page_name;
    }

    public void setPageName(String page_name) {
        this.page_name = page_name;
    }

    String getPageLink() {
        return page_link;
    }

    public void setPageLink(String pagelink) {
        this.page_link = pagelink;
    }


}
