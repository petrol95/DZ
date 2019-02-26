package mvcrestful;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class VisitDAO {

    private static final Map<String, Visit> visitMap = new HashMap<String, Visit>();

    static {
        initVisits();
    }

    private static void initVisits() {
        Visit visit1 = new Visit("E01", "Smith", "Clerk");
        Visit visit2 = new Visit("E02", "Allen", "Salesman");
        Visit visit3 = new Visit("E03", "Jones", "Manager");

        visitMap.put(visit1.getId(), visit1);
        visitMap.put(visit2.getId(), visit2);
        visitMap.put(visit3.getId(), visit3);

    }

    public Visit addVisit(String userID, String pageID) {
        Visit visit = new Visit("E07", userID, pageID);
        visitMap.put(visit.getId(), visit);
        return visit;
    }

    public List<Visit> getAllVisits() {
        Collection<Visit> c = visitMap.values();
        List<Visit> list = new ArrayList<Visit>();
        list.addAll(c);
        return list;
    }

}
