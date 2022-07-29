package sg.edu.iss.kuruma.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.kuruma.model.Car;
import sg.edu.iss.kuruma.service.CarService;

@Controller
public class SearchController {
	
	@Autowired
	CarService cservice;
	
	@RequestMapping("/")
	public String home1(Model model){
		Car car1 = new Car();
		model.addAttribute("car", car1);
		return "home";
	}
	
	@RequestMapping("/home")
	public String home(Model model){
		Car car1 = new Car();
		model.addAttribute("car", car1);
		return "home";
	}
	
	
    @PostMapping("/search")
	public String searchBrand(@ModelAttribute("car") Car car, Model model) {
		String brand = car.getBrand();
		List<Car> list = cservice.findByBrand(brand);
		model.addAttribute("brandlist",list);
		return "brandlist";
	}

    /*@RequestMapping(value = "/list")
	public String list(Model model) {
		model.addAttribute("cars", cservice.listCar());
		return "cars";
	}*/
    
    @RequestMapping("/contactus")
    public String home() {
		return "contactus";
	}
    
    @RequestMapping("/aboutus")
	public String aboutUs(){
		return "aboutus";
	}
}