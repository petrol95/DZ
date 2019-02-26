package mvcrestful;

public class Visit {

    private String id;
    private String userId;
    private String pageId;

    public Visit() {
    }

    public Visit(String id, String userId, String pageId) {
        this.id = id;
        this.userId = userId;
        this.pageId = pageId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }
}

