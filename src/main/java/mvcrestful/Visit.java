package mvcrestful;

import java.time.LocalDate;
import java.util.Date;

public class Visit {

    private int id;
    private int userId;
    private int pageId;
    private LocalDate visitDate;

    public Visit() {
    }

    public Visit(int id, int userId, int pageId, LocalDate visitDate) {
        this.id = id;
        this.userId = userId;
        this.pageId = pageId;
        this.visitDate = visitDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public LocalDate getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDate visitDate) {
        this.visitDate = visitDate;
    }
}

