package sg.edu.iss.kuruma.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import sg.edu.iss.kuruma.model.User;
import sg.edu.iss.kuruma.service.CarService;
import sg.edu.iss.kuruma.service.UserService;

@Controller
@SessionAttributes("username")
@RequestMapping("/admin")
public class AdminController {	
	
	@Autowired
	UserService uservice;
	@Autowired
	CarService cservice;
	
	private List<User>listU;
	
	@RequestMapping("/user")
	public String showUsers(Model model, HttpSession session){
		String username =(String) session.getAttribute("username");
		if (username.equals("Admin")) {
		listU = new ArrayList<User>();
		listU = uservice.showUsers();
		model.addAttribute("userlist",listU);}
		return "admin";
		
	}
	@RequestMapping("/delete/{username}")
	public String deleteUser(@PathVariable("username") String username) {
		uservice.deleteUser(username);
		return "redirect:/admin/user";
	}
	
}
