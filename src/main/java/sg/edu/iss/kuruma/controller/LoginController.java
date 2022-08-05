package sg.edu.iss.kuruma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import sg.edu.iss.kuruma.model.Session;
import sg.edu.iss.kuruma.model.User;
import sg.edu.iss.kuruma.repository.UserRepository;
import sg.edu.iss.kuruma.service.UserService;

@Controller
@SessionAttributes("session")
public class LoginController {
	
	@Autowired
	UserService uservice;
	@Autowired
	UserRepository urepo;
	
	@RequestMapping("/login")
	public String Login(Model model, @ModelAttribute Session session) {
		//User test = new User("2","2","eugeneongjj@gmail.com");
		//urepo.saveAndFlush(test);
		if (session.getUsername() != null) {
			return "redirect:/info";
		}
		model.addAttribute("user", new User());
		return "login";
	}
	
	@PostMapping("/submit")
	public String Authenticate(@ModelAttribute("user") User user, @ModelAttribute Session session, Model model) {
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
			if (session.getLastViewCar() == null) {
				Session currentSession = new Session();
				currentSession.setUserid(u.getUserid());
				currentSession.setUsername(u.getUsername());
				model.addAttribute("session", currentSession);
				return "redirect:/home";
			}
			else {
				model.addAttribute("sessionDetails", session);
				return "forward:/info";
			}
		}
		return "forward:/login";
	}
	
	@RequestMapping("/logout")
	public String Logout(SessionStatus status){
		status.setComplete();
		return "redirect:/login";
	}
	
	private boolean authenticateUser(User user, User userFromDb) {
		SCryptPasswordEncoder encoder = new SCryptPasswordEncoder();
		return encoder.matches(user.getPassword(), userFromDb.getPassword());
	}
	
	@ModelAttribute("session")
	public Session session() {
		return new Session();
	}

}
