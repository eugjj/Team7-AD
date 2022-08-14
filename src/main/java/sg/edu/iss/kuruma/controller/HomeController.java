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
	public String home(Model model, @Param("entry") String entry, HttpSession session) {
		if (session.isNew()) {
			session.setAttribute("username", "Guest");}
		model.addAttribute("entry", entry); 
		return "home"; 
		 }
	
	@RequestMapping("/basicmodel")
	public String loadbasic(Model model, HttpSession session) {
		model.addAttribute("username", session.getAttribute("username"));
		return "basicmodel";
	}
	
	@RequestMapping("/expertmodel")
	public String loadexpert(Model model, HttpSession session) {
		model.addAttribute("username", session.getAttribute("username"));
		return "expertmodel";
	}
}