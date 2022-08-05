package sg.edu.iss.kuruma.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import sg.edu.iss.kuruma.model.Car;
import sg.edu.iss.kuruma.service.CarService;
import sg.edu.iss.kuruma.service.UserService;

@Controller
@SessionAttributes("username")
public class SearchController {
	
	@Autowired
	CarService cservice;
	@Autowired
	UserService uservice;
	
	private List<Car>listByPage;
	private List<Integer>wList=new ArrayList<Integer>();
	private boolean atWishlist = false;
	private int pageNo = 0;
	private String searchEntry; 
	private String username="";
	private final int ITEMS_PER_PAGE = 10;	
	 
	@RequestMapping("/search/sort-{entry}")
    public String sortCar(Model model, @RequestParam("by") String by, @PathVariable("entry") String entry) {
    	if (by.equals("Price")) {
    		listByPage = new ArrayList<Car>();
    		listByPage = cservice.sortSearchByPrice(searchEntry);
    		pageNo = 0;
    		return "forward:/search/0";
    	}
    	// neeed to update when the other sorting buttons are there
    	else return "home";
    }
	 
	 @GetMapping("/search")
	 public String showAllCars(@Param("entry") String entry, Model model, HttpSession session) {
		 	try {
		 	username =(String) session.getAttribute("username");
		 	wList = uservice.getWishlist(username);
	    	List<Car> list = cservice.findAllCars();
	    	model.addAttribute("searchlist",list);
	    	model.addAttribute("wishlist",wList);
	    	model.addAttribute("entry", entry);
			return "searchlist";}
		 	catch(Exception e){
		 		return "forward:/home";}
		}
	
    @PostMapping("/search")
	public String searchCar(@RequestParam("entry") String entry, Model model, HttpSession session) {
    	if(entry != null) {
    		searchEntry = entry;
    		listByPage = new ArrayList<Car>();
        	listByPage = cservice.findSearchByEntry(searchEntry);}
    	atWishlist = false;
    	username =(String) session.getAttribute("username");
    	List<Car> result = new ArrayList<Car>();
    	if (listByPage.size() > 0) {
    		for(int i=0; i<ITEMS_PER_PAGE; i++) {
    			result.add(listByPage.get(i));}}
    	pageNo = 0;
    	wList = uservice.getWishlist(username);
    	model.addAttribute("searchlist",result);
    	model.addAttribute("wishlist",wList);
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
    	pageNo = page-1;    	
    	model.addAttribute("currentPage", page);
    	model.addAttribute("wishlist",wList);
    	model.addAttribute("searchlist", result);
    	return "searchlist"; 
    	}
    
    @RequestMapping("/search/backward/{currentPage}")
    public String searchCarsPrev(@PathVariable(value = "currentPage") int page, Model model) {
    	atWishlist = false;    	
    	List<Car> result = new ArrayList<Car>();
    	if (1 == page) {
    		for(int i=0; i<ITEMS_PER_PAGE; i++) {
				result.add(listByPage.get(i));
			}
    	}
    	else {
			int max = --page * ITEMS_PER_PAGE;
			int start = --page * ITEMS_PER_PAGE;
			
			if (max > listByPage.size()) {
				max = listByPage.size();
			}
			for(int i=start; i<max; i++) {
				result.add(listByPage.get(i));
			}
			++page;
    	}
    	pageNo = page-1;
    	model.addAttribute("currentPage", page);
    	model.addAttribute("wishlist",wList);
    	model.addAttribute("searchlist", result);
    	return "searchlist";	
    }
    
    @RequestMapping("/search/{currentPage}")
    public String searchCarsCurrent(@PathVariable("currentPage") int page, Model model) {
    	List<Car> result = new ArrayList<Car>();
    	if (listByPage.size() > 0) {
    		if ((listByPage.size() / ITEMS_PER_PAGE) + 1 >= page) {
    			int start = page * ITEMS_PER_PAGE;
    			int max = (page+1) * ITEMS_PER_PAGE;
    			if (max > listByPage.size()) {
    				max = listByPage.size();
    			}
    			for(int i=start; i<max; i++) {
    				result.add(listByPage.get(i));
    			}
    		}
    	}
    	wList = uservice.getWishlist(username);
    	int pageNoModel = pageNo+1;
    	model.addAttribute("currentPage", pageNoModel);
    	model.addAttribute("wishlist", wList);
    	model.addAttribute("searchlist", result);
    	return "searchlist";
    	}
    
    @RequestMapping("/cardetail/{id}")
    public String cardetail(@PathVariable("id") Integer id, Model model) {      
        Car cardetails = cservice.findById(id);
        model.addAttribute("carD",cardetails);
        return "cardetail";
    }
    
    @RequestMapping("/add/{id}")
    public String addToUserWishlist(@PathVariable("id") int carID) {
    	if(!username.equals("Guest")){
    		Car car = cservice.findById(carID);    	
    		uservice.addToWishlist(car, username);
    	return "forward:/search/"+pageNo;}
    	else
    		return "forward:/login";
    }
    
    @RequestMapping("/user/{username}")
	public String wishlist(@PathVariable("username") String uname, Model model) {
    	username = uname;
    	wList = uservice.getWishlist(username);
		model.addAttribute("searchlist",uservice.findByUsername(username).getWishlist());
		model.addAttribute("wishlist",wList);
		atWishlist = true;
		return "searchlist";
	}
    
    @RequestMapping("/remove/{id}")
	public String removeFromWishlist(@PathVariable("id") int carID) {
    	Car car = cservice.findById(carID);
    	uservice.removeFromWishlist(car, username);
    	if (atWishlist) {
    		return "forward:/user/"+username;
    	}
    	else
		return "forward:/search/"+pageNo;
	}    

}