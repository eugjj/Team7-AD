package sg.edu.iss.kuruma.controller;

//import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

import sg.edu.iss.kuruma.model.Car;
import sg.edu.iss.kuruma.service.CarService;

@CrossOrigin
@RestController
@RequestMapping (value="/api")
public class CarController {
    @Autowired
    CarService cservice;
  
    @RequestMapping(value = "/add")
    public ResponseEntity<Car> createAccount(@RequestBody Car car) {
        try {
                cservice.saveCar(car);
                return new ResponseEntity<>(car, HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
            }
    }
    
}
