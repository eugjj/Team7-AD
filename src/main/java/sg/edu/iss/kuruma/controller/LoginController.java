package sg.edu.iss.kuruma.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.kuruma.model.User;
import sg.edu.iss.kuruma.repository.UserRepository;
import sg.edu.iss.kuruma.service.UserService;

@Controller
public class LoginController {
	
	@Autowired
	UserService uservice;
	
	@RequestMapping("/login")
	public String Login(Model model) {		
		model.addAttribute("user", new User());
		return "login";
	}
	
	@RequestMapping("/register")
	public String register(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}
	
	@RequestMapping("/register/new")
	public String registerNew(@ModelAttribute("user") User user, Model model) {
		User u = new User (user.getUsername(),user.getPassword(),user.getEmail());
		if (uservice.findUserByUsername(u.getUsername()) == null) {
		uservice.addUser(u);
		return "home";}
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
		
		User u = uservice.findUserByUsername(user.getUsername());
		
		if (u == null) {
			return "forward:/login";
		}
		
		if (authenticateUser(user, u)) {
			session.setAttribute("username", u.getUsername());
			return "forward:/home";
		}
		return "forward:/login";
	}
	
	@RequestMapping("/logout")
	public String Logout(HttpSession session){
		int id = (int) session.getAttribute("userId");
		User user = uservice.findUserById(id);
		if (user != null) {
			session.removeAttribute("userId");
			session.removeAttribute("username");
			session.invalidate();
		}
		return "forward:/home";
	}
	
	private boolean authenticateUser(User user, User userFromDb) {
		SCryptPasswordEncoder encoder = new SCryptPasswordEncoder();
		return encoder.matches(user.getPassword(), userFromDb.getPassword());
	}

}
