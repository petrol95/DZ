package mvcrestful;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
public class MainRESTController {

    @Autowired
    private VisitDAO visitDAO;
    private LocalDate dateFrom;
    private LocalDate dateTo;

    @RequestMapping("/")
    @ResponseBody
    public String welcome() {
        return "Welcome to RestTemplate Example";
    }

    // GET
    // http://localhost:8888/visits?dateFrom=${dateFrom}dateTo=${dateTo}
    @GetMapping("/visits")
    public Map getVisits(@RequestParam("dateFrom") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam("dateTo") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo) {
        return visitDAO.getStat(dateFrom, dateTo);
    }

    // POST
    // http://localhost:8888/visits/${userID}/${pageID}
    @RequestMapping(value = "/visits/{userID}/{pageID}", //
            method = RequestMethod.POST, //
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Map addVisit(@PathVariable("userID") int userID, @PathVariable("pageID") int pageID) {
        return visitDAO.insertVisit(userID, pageID);
    }

}


