package sg.edu.iss.kuruma.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("username")
public class HomeController {
	@RequestMapping("/contactus")
    public String contactus() {
		return "contactus";
	}
    
    @RequestMapping("/aboutus")
	public String aboutUs(){
		return "aboutus";
	}
	
	@RequestMapping("/home") 
	public String home(Model model, @Param("entry") String entry, HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.isNew()) {
			session.setAttribute("username", "Guest");
		model.addAttribute("username", "Guest");}
		model.addAttribute("entry", entry); 
		return "home"; 
		 }
}