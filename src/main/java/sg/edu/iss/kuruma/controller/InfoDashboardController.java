package sg.edu.iss.kuruma.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import sg.edu.iss.kuruma.model.Session;

@Controller
public class InfoDashboardController {
	
	@RequestMapping("/info")
	public String loadInfo(@SessionAttribute("session") Session session, Model model) {
		model.addAttribute("sessionDetails", session);
		return "info";
	}

}
