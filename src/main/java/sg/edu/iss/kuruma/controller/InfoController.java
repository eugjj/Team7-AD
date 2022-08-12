package sg.edu.iss.kuruma.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import sg.edu.iss.kuruma.model.Car;
import sg.edu.iss.kuruma.service.CarService;

@Controller
public class InfoController {
	@Autowired
	CarService cservice;
	Car cardetails;
	List<Car> similarCarList = new ArrayList<Car>();
	List<Car> bestValueList = new ArrayList<Car>();
	int SHOW_MODEL_NUM = 4;
	
	@RequestMapping("/info")
    public String loadInfo(Model model, HttpSession session) {
		// from session get the last search car 
		Integer carid = (Integer) session.getAttribute("lastcarviewed");
		cardetails = cservice.findById(carid);
		
		// get SHOW_MODEL_NUM number to show similar car models based on last search car
		similarCarList = cservice.getSimilarCarModels(getGenericModelString(cardetails))
				.stream()
				.limit(SHOW_MODEL_NUM)
				.collect(Collectors.toList());
		
		// get SHOW_MODEL_NUM number to show best value (based on listing and predicted price)
		bestValueList = cservice.findAllCars().stream()
					.sorted((c1,c2)->cservice.calcValue(c1).compareTo(cservice.calcValue(c2)))
					.limit(SHOW_MODEL_NUM)
					.collect(Collectors.toList());
		
		model.addAttribute("similarCarModels", similarCarList);
		model.addAttribute("bestBuy", bestValueList);
		model.addAttribute("prevCarSearched", cardetails);
		model.addAttribute("username", session.getAttribute("username"));
        return "info";
	}
	
	public String getGenericModelString(Car cardetails) {
		String carModel = cardetails.getModel();
		String [] strList = carModel.split(" ");
		return strList[0] + " " + strList[1];
	}
}
