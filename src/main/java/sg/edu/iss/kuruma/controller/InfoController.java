package sg.edu.iss.kuruma.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import sg.edu.iss.kuruma.model.Car;
import sg.edu.iss.kuruma.service.CarService;
import sg.edu.iss.kuruma.service.UserService;

@Controller
public class InfoController {
	@Autowired
	CarService cservice;
	
	@RequestMapping("/info")
    public String loadInfo(Model model, HttpSession session) {
		Integer carid = (Integer) session.getAttribute("lastcarviewed");
		Car cardetails = cservice.findById(carid);
		model.addAttribute("sessionDetails", cardetails);
        return "info";
}
}
