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
	@Autowired
	UserRepository urepo;
	
	@RequestMapping("/login")
	public String Login(Model model) {
		//User test = new User("2","2","eugeneongjj@gmail.com");
		//urepo.saveAndFlush(test);
		model.addAttribute("user", new User());
		return "login";
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
			session.setAttribute("userId", u.getUserid());
			session.setAttribute("userName", u.getUsername());
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
			session.removeAttribute("userName");
			session.invalidate();
		}
		return "forward:/home";
	}
	
	private boolean authenticateUser(User user, User userFromDb) {
		SCryptPasswordEncoder encoder = new SCryptPasswordEncoder();
		return encoder.matches(user.getPassword(), userFromDb.getPassword());
	}

}
