package Controllers;

import Service.RequestProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Павло on 29.06.2017.
 */
@RestController
@RequestMapping(value = "/pr")
public class ProcessRequestController {

    @Autowired
    private RequestProcessingService requestProcessingService;

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ResponseEntity<Void> add(@RequestParam("username") String username, @RequestParam("reponame") String reponame, HttpServletRequest request) throws IOException {
        String token = request.getHeader("Authentication");
        requestProcessingService.add(username,reponame, token);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/add-repository", method = RequestMethod.POST)
    public ResponseEntity<Void> addRepository(@RequestParam("repository-name") String repositoryName, HttpServletRequest request) throws IOException {
        String token = request.getHeader("Authentication");
        boolean result = requestProcessingService.addRepo(repositoryName, token);
        return  result ? new ResponseEntity<Void>(HttpStatus.OK) : new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

}
