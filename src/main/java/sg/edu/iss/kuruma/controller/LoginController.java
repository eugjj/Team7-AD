package sg.edu.iss.kuruma.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import sg.edu.iss.kuruma.model.User;
import sg.edu.iss.kuruma.service.UserService;

@Controller
public class LoginController {
	
	@Autowired
	UserService uservice;
	
	@RequestMapping("/login")
	public String Login(Model model, HttpSession session) {		
		model.addAttribute("user", new User());
		model.addAttribute("username", session.getAttribute("username"));
		return "login";
	}
	
	@RequestMapping("/register")
	public String register(Model model, HttpSession session) {
		model.addAttribute("user", new User());		
		model.addAttribute("username",(String)session.getAttribute("username"));
		return "register";
	}
	
	@RequestMapping("/register/new")
	public String registerNew(@ModelAttribute("user") User user, Model model) {
		User u = new User (user.getUsername(),user.getPassword(),user.getEmail());
		if (uservice.findByUsername(u.getUsername()) == null) {
		uservice.addUser(u);
		model.addAttribute("message", "success");
		return "forward:/register";}
		else
			model.addAttribute("message", "repeat username");
		return "forward:/register";
	}
	
	@PostMapping("/submit")
	public String Authenticate(@ModelAttribute("user") User user, HttpSession session) {
		if (user.getUsername() == null ||
				user.getPassword() == null ||
				user.getUsername().isBlank() ||
				user.getPassword().isBlank()) {
			return "forward:/login";
		}
		
		User u = uservice.findByUsername(user.getUsername());
		
		if (u == null) {
			return "forward:/login";
		}
		if (authenticateUser(user, u)) {
			session.setAttribute("username", u.getUsername());
			System.out.println(session.getAttribute("username"));
			return "forward:/info";
		}
		return "forward:/login";
	}
	
	@RequestMapping("/logout")
	public String Logout(HttpSession session){
			session.setAttribute("username","Guest");
		return "redirect:/home";
	}
	
	private boolean authenticateUser(User user, User userFromDb) {
		SCryptPasswordEncoder encoder = new SCryptPasswordEncoder();
		return encoder.matches(user.getPassword(), userFromDb.getPassword());
	}

	@RequestMapping("/change")
	public String changePassword(Model model, HttpSession session){
		model.addAttribute("user",new User());
		model.addAttribute("username", session.getAttribute("username"));
		//create plain object to use in html
       return "changePassword";
	}
	
	@PostMapping("/confirm")
	public String changing(@ModelAttribute("user")User user)//come from html
	{
	   User u=uservice.findByUsername(user.getUsername());
	   if(u==null) {
		   return "forward:/login";
	   }
	   else {
		   if(authenticateUser(user,u)) {
			   SCryptPasswordEncoder encoder = new SCryptPasswordEncoder();
			   String hashedPassword = encoder.encode(user.getNewPassword());
			   u.setPassword(hashedPassword);
			   uservice.save(u);
		   }
		   return "forward:/login";
	   }
	}



}
