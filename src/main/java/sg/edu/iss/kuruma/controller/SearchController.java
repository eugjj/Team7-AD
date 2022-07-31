package sg.edu.iss.kuruma.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	 @RequestMapping("/home") public String home(Model model, @Param("entry") String entry) { 
		 model.addAttribute("entry", entry); 
		 return "home"; 
		 }
	 
	 @GetMapping("/search")
	 public String showAllCars(@Param("entry") String entry, Model model) {
	    	List<Car> list = cservice.findAllCars();
	    	model.addAttribute("searchlist",list);
	    	model.addAttribute("entry", entry);
			return "searchlist";
		}
	
    @PostMapping("/search")
	public String searchCar(@Param("entry") String entry, Model model) {
    	List<Car> list = cservice.findSearchByEntry(entry);
    	model.addAttribute("searchlist",list);
    	model.addAttribute("entry", entry);
		return "searchlist";
	}

    @RequestMapping("/contactus")
    public String home() {
		return "contactus";
	}
    
    @RequestMapping("/aboutus")
	public String aboutUs(){
		return "aboutus";
	}
}