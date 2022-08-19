package sg.edu.iss.kuruma.controller;

import java.net.http.HttpResponse;
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
import org.springframework.web.client.RestTemplate;

import sg.edu.iss.kuruma.model.Car;
import sg.edu.iss.kuruma.model.User;
import sg.edu.iss.kuruma.repository.UserRepository;
import sg.edu.iss.kuruma.service.CarService;
import sg.edu.iss.kuruma.service.EmailNotificationService;
import sg.edu.iss.kuruma.service.UserService;

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
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        try {
            // ----> need to add in logic to check if car already in db    
        	cservice.saveCar(car);
        	sendEmailNotification(car);
                return new ResponseEntity<>(car, HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
            }
    }
    
    @PostMapping(value="/emailnotice", consumes="application/json", produces="application/json")
    public String sendEmailNotification(@RequestBody Car car) {
    	try {
    		eservice.sentEmailNotification(eservice.userWithcarPriceLessThanWishlist(car));
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
     
     @RequestMapping("/py")
     public ResponseEntity<?> startPython() {
    	 try {
    	        String uri="http://127.0.0.1:5000/start";
    	        RestTemplate restTemplate = new RestTemplate();
    	       
    	        ResponseEntity<String> result = restTemplate.postForEntity(uri, "", String.class);
    	        return new ResponseEntity<>( result.getStatusCodeValue() == 200 ? "Db created successfully" : "Db Not created successfully", HttpStatus.OK);
    	    }catch (Exception e){
    	        e.printStackTrace();
    	        return new ResponseEntity<>("Error!, Please try again", HttpStatus.INTERNAL_SERVER_ERROR);
    	    }
     }
     
    
}
