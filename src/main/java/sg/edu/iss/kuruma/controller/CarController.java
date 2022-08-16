package sg.edu.iss.kuruma.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.iss.kuruma.model.Car;
import sg.edu.iss.kuruma.model.User;
import sg.edu.iss.kuruma.repository.UserRepository;
import sg.edu.iss.kuruma.service.CarService;
import sg.edu.iss.kuruma.service.EmailNotificationService;

@CrossOrigin
@RestController
@RequestMapping (value="/api")
public class CarController {
    @Autowired
    CarService cservice;
    @Autowired
    EmailNotificationService eservice;
    @Autowired
    UserRepository urepo;
  
    @RequestMapping(value = "/add")
    public ResponseEntity<Car> createAccount(@RequestBody Car car) {
        try {
            // ----> need to add in logic to check if car already in db    
        	cservice.saveCar(car);
        	
        	// --> once save newcar into db need to add in logic to check against wishlist and if newcar's price < wishlist car's price
        	// then do postrequest sendEmailNotification
         
                return new ResponseEntity<>(car, HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
            }
    }
    
    @PostMapping(value="/emailnotice", consumes="application/json", produces="application/json")
    public String sendEmailNotfication(@RequestBody User user) {
    	try {
    		// seed user for testing
    		User user1 = new User (user.getEmail());
    		urepo.save(user1);
    		eservice.sentEmailNotification(user1);
    		return "Email notification sent!";
    	}
    	catch (Exception ex) {
    		return "Error when sending email: " + ex;
    	}
    }
    
     @RequestMapping("/{query}")
    public List<Car> getCarByQuery(@PathVariable("query")String query){
    	List<Car> cars = cservice.findSearchByEntry(query);
    	return cservice.androidList(cars);
    }
    
}
