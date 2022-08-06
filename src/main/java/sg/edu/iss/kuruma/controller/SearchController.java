package sg.edu.iss.kuruma.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

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
import org.springframework.web.bind.annotation.SessionAttribute;

import sg.edu.iss.kuruma.model.Car;
import sg.edu.iss.kuruma.model.Session;
import sg.edu.iss.kuruma.model.User;
import sg.edu.iss.kuruma.repository.UserRepository;
import sg.edu.iss.kuruma.service.CarService;
import sg.edu.iss.kuruma.service.UserService;

@Controller
public class SearchController {
	
	@Autowired
	CarService cservice;
	@Autowired
	UserService uservice;
	
	private List<Car>listByPage;
	private List<Car> listW = new ArrayList<Car>();
	private boolean atWishlist = false;
	private int pageNo = 0;
	private String searchEntry;
	private final int ITEMS_PER_PAGE = 10;
	
	 
	@RequestMapping("/search/sort-{entry}")
    public String sortCar(Model model, @RequestParam("by") String by, @PathVariable("entry") String entry) {
    	if (by.equals("Price")) {
    		listByPage = new ArrayList<Car>();
    		listByPage = cservice.sortSearchByPrice(searchEntry);
    		return "forward:/search/0";
    	}
    	// neeed to update when the other sorting buttons are there
    	else return "home";
    }
	 
	 @GetMapping("/search")
	 public String showAllCars(@Param("entry") String entry, Model model) {
	    	List<Car> list = cservice.findAllCars();
	    	model.addAttribute("searchlist",list);
	    	model.addAttribute("entry", entry);
			return "searchlist";
		}
	
    @PostMapping("/search")
	public String searchCar(@RequestParam("entry") String entry, Model model, @SessionAttribute("session") Session session) {
    	if(entry != null) {
    		searchEntry = entry;
    		listByPage = new ArrayList<Car>();
        	listByPage = cservice.findSearchByEntry(searchEntry);}
    	
    	atWishlist = false;
    	if (session.getUsername() != null) {
    		listW = uservice.getUser(session.getUserid()).getWishlist();
    		model.addAttribute("wishlist",listW);
    	}
    	else {
    		model.addAttribute("wishlist",listW);
    	}
    	
    	List<Car> result = new ArrayList<Car>();
    	if (listByPage.size() > 0) {
    		for(int i=0; i<ITEMS_PER_PAGE; i++) {
    			result.add(listByPage.get(i));}}
    	pageNo = 0;
    	model.addAttribute("searchlist",result);
    	model.addAttribute("entry", entry);
    	model.addAttribute("currentPage",1);
		return "searchlist";
	}
    
    @RequestMapping("/search/forward/{currentPage}")
    public String searchCarsNext(@PathVariable(value = "currentPage") int page, Model model, @SessionAttribute("session") Session session) {
    	if (session.getUsername() != null) {
    		listW = uservice.getUser(session.getUserid()).getWishlist();
    		model.addAttribute("wishlist",listW);
    	}
    	else {
    		model.addAttribute("wishlist",listW);
    	}
    	
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
    	model.addAttribute("searchlist", result);
    	return "searchlist"; 
    	}
    
    @RequestMapping("/search/backward/{currentPage}")
    public String searchCarsPrev(@PathVariable(value = "currentPage") int page, Model model, @SessionAttribute("session") Session session) {
    	atWishlist = false;
    	if (session.getUsername() != null) {
    		listW = uservice.getUser(session.getUserid()).getWishlist();
    		model.addAttribute("wishlist",listW);
    	}
    	else {
    		model.addAttribute("wishlist",listW);
    	}
    	
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
    	model.addAttribute("searchlist", result);
    	return "searchlist";	
    }
    
    @RequestMapping("/search/{currentPage}")
    public String searchCarsCurrent(@PathVariable(value = "currentPage") int page, Model model, @SessionAttribute("session") Session session) {
    	if (session.getUsername() != null) {
    		listW = uservice.getUser(session.getUserid()).getWishlist();
    		model.addAttribute("wishlist",listW);
    	}
    	else {
    		model.addAttribute("wishlist",listW);
    	}
    	
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
    	int pageNoModel = pageNo+1;
    	model.addAttribute("currentPage", pageNoModel);
    	model.addAttribute("searchlist", result);
    	return "searchlist"; 
    	}
    
    @RequestMapping("/cardetail/{id}")
    public String cardetail(@PathVariable("id") Integer id, Model model, @SessionAttribute("session") Session session) {      
    	Car cardetails = cservice.findById(id);
    	session.setLastViewCar(cardetails);
        model.addAttribute("carD",cardetails);
        return "cardetail";
    }
    
    @RequestMapping("/add/{id}")
    public String addToUserWishlist(@PathVariable("id") int carID) {
    	Car car = cservice.findById(carID);
    	uservice.addToWishlist(car);
    	return "forward:/search/"+pageNo;
    }
    
    @RequestMapping("/user/{id}")
	public String wishlist(@PathVariable("id") Integer userID, Model model) {
    	User user = uservice.getUser(userID);
		List<Car> list = user.getWishlist();
		model.addAttribute("searchlist",list);
		model.addAttribute("wishlist",list);
		if (list.size()!=0) {
		atWishlist = true;
		return "searchlist";}
		else
			return "home";
	}
    
    @RequestMapping("/remove/{id}")
	public String removeFromWishlist(@PathVariable("id") int carID) {
//to implement getting Userid from session
    	Car car = cservice.findById(carID);
    	uservice.removeFromWishlist(car);
    	
    	if (atWishlist) {
    		return "forward:/user/"+"1";
    	}
    	else
		return "forward:/search/"+pageNo;
	}    

}