package mvcrestful;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainRESTController {

    @Autowired
    private VisitDAO visitDAO;

    @RequestMapping("/")
    @ResponseBody
    public String welcome() {
        return "Welcome to RestTemplate Example";
    }

    // URL:
    // http://localhost:8888/visits
//    @RequestMapping(value = "/visits", //
//            method = RequestMethod.GET, //
//            produces = {MediaType.APPLICATION_JSON_VALUE})
//    @ResponseBody
//    public List<Visit> getVisits() {
//        List<Visit> list = visitDAO.getAllVisits();
//        return list;
//    }

//     URL:
//     http://localhost:8888/visits/{userID}/{pageID}
    @RequestMapping(value = "/visits/{userID}/{pageID}", //
            method = RequestMethod.POST, //
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Map addVisit(@PathVariable("userID") int userID, @PathVariable("pageID") int pageID) {
        return visitDAO.insertVisit(userID, pageID);
    }

}


