package sg.edu.iss.kuruma.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.iss.kuruma.model.Car;
import sg.edu.iss.kuruma.service.CarService;

@Controller
public class SearchController {
	
	@Autowired
	CarService cservice;
	
	private List<Car>listByPage;
	private final int ITEMS_PER_PAGE = 20;
	
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
	public String searchCar(@RequestParam("entry") String entry, Model model) {
    	listByPage = new ArrayList<Car>();
    	listByPage = cservice.findSearchByEntry(entry);
    	List<Car> result = new ArrayList<Car>();
    	if (listByPage.size() > 0) {
    		for(int i=0; i<ITEMS_PER_PAGE; i++) {
    			result.add(listByPage.get(i));}}
    	
    	model.addAttribute("searchlist",result);
    	model.addAttribute("entry", entry);
    	model.addAttribute("currentPage",1);
		return "searchlist";
	}
    
    @RequestMapping("/search/forward/{currentPage}")
    public String searchCarsNext(@PathVariable(value = "currentPage") int page, Model model) {
    	List<Car> result = new ArrayList<Car>();
    	if (listByPage.size() / ITEMS_PER_PAGE < page) {
    		page--;
    	
    	}
    	else if (ITEMS_PER_PAGE * page == listByPage.size()) {
    		page--;
    	}
    	if (listByPage.size() > 0) {
    		if ((listByPage.size() / ITEMS_PER_PAGE) + 1 >= page) {
    			int start = page * ITEMS_PER_PAGE;
    			int max = ++page * ITEMS_PER_PAGE;
    			if (max > listByPage.size()) {
    				max = listByPage.size();
    			}
    			for(int i=start; i<max; i++) {
    				result.add(listByPage.get(i));
    			}
    		}
    	}
    	model.addAttribute("currentPage", page);
    	model.addAttribute("searchlist", result);
    	return "searchlist"; 
    	}
    
    @RequestMapping("/cardetail/{id}")
    public String cardetail(@PathVariable("id") Integer id, Model model) {      
        Car cardetails = cservice.findById(id);
        model.addAttribute("carD",cardetails);
        return "cardetail";
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