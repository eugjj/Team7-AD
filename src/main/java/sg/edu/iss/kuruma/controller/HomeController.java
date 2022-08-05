package sg.edu.iss.kuruma.controller;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	@RequestMapping("/contactus")
    public String contactus() {
		return "contactus";
	}
    
    @RequestMapping("/aboutus")
	public String aboutUs(){
		return "aboutus";
	}
	
	@RequestMapping("/home") public String home(Model model, @Param("entry") String entry) { 
		 model.addAttribute("entry", entry); 
		 return "home"; 
		 }
}